# ControleServicos (GestaoServicosAutomotivos)

Sistema de gestão de serviços para oficinas mecânicas (multi-tenant), com
ordens de serviço, clientes, veículos, itens (peças/serviços), gestão de
usuários/permissões e um módulo de IA para criar rascunhos de ordem de
serviço a partir de áudio.

## Stack
- **Java 17 / Spring Boot 3.3.0**
- Spring Data JPA + PostgreSQL + Flyway (migrations em `src/main/resources/db/migration`)
- Spring Security + OAuth2 Resource Server (JWT via chaves RSA `app.key.pem` / `app.public.key.pem`)
- springdoc-openapi (Swagger em `/api-docs`)
- iText (geração de PDF)
- Lombok, MapStruct-like mappers manuais
- Testes: JUnit 5 + Testcontainers (Postgres)

Build: `mvn` (projeto Maven, `pom.xml`).
Contexto da aplicação: `/controle-servicos` (ver `application.yml`).

## Estrutura de pacotes (`src/main/java/br/com/vital/controle_servico`)

Cada módulo segue o padrão: `controller / domain / dto / exception / mapper / repository / service (+ impl)`.

### `auth`
- `SecurityConfig`, `JwtAuthFilter`, `JwtService`, `AuthenticationService`, `UserDetailsServiceImpl`
- Anotações de permissão: `@IsRead`, `@IsCreate`, `@IsUpdate`, `@IsDelete`
- `AuthController` (login/token)

### `users`
- `User`, `Role`, `Permission` (entidades)
- `UserController`, `UserRepository`, `RoleRepository`
- `UserRegistrationDTO`

### `tenants` (multi-tenant)
- `Tenant` (entidade), `TenantController`, `TenantRegistrationService`, `TenantRegistrationDTO`
- `TenantContext` — `ThreadLocal<UUID>` com o tenant da requisição atual
- `TenantFilter` (servlet filter que popula o `TenantContext`)
- `TenantFilterAspect` — aspecto que aplica filtro de tenant nas queries Hibernate
- `TenantEntityListener` — listener JPA que preenche `tenantId` automaticamente ao persistir

### `subscriptions` (planos/pacotes)
- `Plan`, `Feature` (enum), `Subscription`, `SubscriptionStatus`, `TenantAddon`
- `FeatureAccessService` / `FeatureAccessServiceImpl` — valida se um tenant tem acesso a uma feature do plano
- `SubscriptionRepository`, `TenantAddonRepository`

### `customers`
- `Customer`, `Address` (entidades)
- CRUD completo: controller, service, mapper, repository + `CustomerCriteriaRepository` (filtros dinâmicos)
- `CustomerStatusResponseDTO`, `CustomerFilterDTO`

### `vehicles`
- `Vehicle`, `FuelType` (entidades/enum)
- CRUD completo (controller/service/mapper/repository + criteria)
- `CustomerVehicleDTO`, `VehicleFilterDTO`

### `itens` (peças/serviços do catálogo)
- `Item`, `ItemType`, `MeasurementType`
- CRUD completo + `ItemCriteriaRepository`
- `ItemOrderRequestDTO` (usado para vincular itens a uma ordem)

### `order_service` (núcleo do sistema)
- Domínio: `OrderService`, `OrderServiceDetail`, `OrderServiceStatus`, `OrderServiceType`
- Funcionalidades concluídas:
  - Criação de ordem de serviço (`NewOrderServiceService`)
  - Detalhe da ordem (`DetailOrderServiceService`, `DetailOrderServiceResponseDTO`)
  - Atualização de status (`UpdateStatusOrderServiceService`)
  - Listagem com paginação/filtros (`OrderServiceFilterDTO`, `OrderServiceCriteriaRepository`)
  - Export em PDF (`BuildPDFDetailOrderService`, iText)
  - Exceções: `OrderServiceNotFoundException`, `VehicleNotBelongToCustomer`
- Campos adicionados na V15 para suportar IA: `partsCost`, `laborCost`,
  `discountAmount`, `customerComplaint`, `inspectionNotes`, `aiDamageReport`
- **Integração com IA** (ver seção abaixo): `OrderAiIntegrationService` /
  `OrderAiIntegrationServiceImpl`, `OrderServiceVoiceController`

### `ai_voice` — módulo de IA (OpenAI)
- `VoiceProcessingService` / `VoiceProcessingServiceImpl`
  - Recebe um áudio (`MultipartFile`)
  - Transcreve com **Whisper** (`whisper-1`, endpoint `/v1/audio/transcriptions`)
  - Envia a transcrição para **GPT-4o-mini** (`/v1/chat/completions`) com um
    prompt especializado em oficina mecânica (corrige erros fonéticos tipo
    "limpeza dos micos" → "limpeza de bicos")
  - Retorna `ExtractedOrderDataDTO` (queixa do cliente, peças sugeridas,
    serviços sugeridos — `SuggestedItemDTO`)
  - Erros encapsulados em `OpenAiIntegrationException`
- Fluxo completo: `OrderServiceVoiceController` (`POST
  /api/v1/orders-services/voice-draft`, multipart) → `OrderAiIntegrationServiceImpl`
  → chama `VoiceProcessingService` → cria um `OrderService` rascunho
  (status `PENDING`, type `ANALYSIS`) com `customerComplaint` e
  `inspectionNotes` preenchidos a partir da IA, vinculado a customer/vehicle/tenant.

### `common`
- `CriteriaRepository` (base para os repositórios de filtro dinâmico)
- `ResponseExceptionHandler` / `ApiError` (tratamento global de exceções)
- `SwaggerConfig`, `JacksonConfig`, `MapperConfiguration`
- `BigDecimalFormat`, `UnauthorizedException`

## Migrations Flyway (`src/main/resources/db/migration`)
| Versão | Conteúdo |
|---|---|
| V1 | criação de `customer` |
| V2/V3 | criação/alteração de `vehicle` |
| V4/V6 | criação/alteração de `itens` |
| V5/V7 | criação/alteração de `order_service` |
| V8 | índice em `order_service_detail_itens` |
| V9 | tabela de usuários |
| V10/V11 | roles/permissions + usuários default |
| V12/V13 | base multi-tenant + `tenant_id` nas tabelas de negócio |
| V14 | módulo de subscriptions (plans, features, subscriptions, tenant_addons) |
| V15 | colunas de IA na `order_service` (parts_cost, labor_cost, discount_amount, customer_complaint, inspection_notes, ai_damage_report) + `tenant_id` em `order_service_detail_itens` |

## Status da IA — o que já existe
- ✅ Integração funcional com **OpenAI** (Whisper + GPT-4o-mini) via `RestClient`
- ✅ Endpoint `POST /api/v1/orders-services/voice-draft` (multipart: audio + customerId + vehicleId + tenantId)
- ✅ Pipeline: áudio → transcrição → extração estruturada (JSON) → criação de ordem de serviço rascunho
- ⚠️ **Configuração pendente**: `openai.api.key` está vazia em `application.yml`
  (precisa vir de variável de ambiente — há um `# TODO` similar para
  credenciais do banco)
- ⚠️ Coluna `ai_damage_report` (V15) ainda não é populada por nenhum service
  — possível próximo passo (ex: análise de imagem/foto de avaria)
- ⚠️ Sem testes automatizados para `ai_voice` / `OrderAiIntegrationService`

## Testes
- `src/test/java/.../config`: infraestrutura de testes de integração
  (Testcontainers Postgres, `AbstractIntegrationTest`, `ApplicationNoSecurity`)
- Único teste de integração existente: `CustomersControllerIT`
- Demais módulos (vehicles, itens, order_service, ai_voice, tenants,
  subscriptions, users, auth) **sem testes de integração ainda**

## Pendências / TODOs conhecidos
- Configurar `openai.api.key` via variável de ambiente
- Configurar credenciais do datasource via variável de ambiente
- Cobertura de testes para os módulos mais novos (multi-tenant, subscriptions, ai_voice, order_service)
