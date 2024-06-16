## Tarefa que precisam serem feitas para concluir o MVP

- [X]  Filtrar as order e gerar api de listagem
- [X]  API de mudança de status e conclusão de API
- [ ]  Gerar exportação em PDF da ordem
- [ ]  Ajustar o @Transactional nos repository
- [ ]  Ajustar validações com o bean validation nos endpoints
- [ ]  Verificar os todos que estão marcados no código
- [ ]  Criar serviços para retornar os tipos que estão nos Enums
- [ ]  Criar modulo de usuário e perfis
- [ ]  modificar as tabelas para que registrar quem foi o autor da ação
- [ ]  implementar jwt
- [ ]  implementar os testes unitiários e integrados de cada parte
- [ ]  Implementar o serviço na cloud
- [ ]  Mudar o nome das entidades de entradas para por Exemplo CustomerRequestDTO
- [ ]  Mudar o nome das entidades de saídas/retorno para por Exemplo CustomerResponseDTO
- [ ]  Mudar as mensagens que estão em inglês para portugues
- [ ]  Mudar logs para português ?
- [ ]  Ajustar os retornos dos endpoints que estão retornando true, verificar se vale a pena retornar um objeto
- [ ]  Analisar bug na idempotencia no cadastro de item
- [ ] Ao final de cada ordem concluída reduzir a quantidade de itens aos itens associados da ordem
- [ ] Analisar a necessidade de criar duas colunas na order para armagenar as datas de inicio do serviço e final
- [ ] Criar um index na tabela de order_servce_detail entre id e item_id(Está com o sep scan)
- [X]  Resolver problema do não lancamento de exception do bean validation
- [X] Criar index na tabela de itens
- [X] Validar se o veículo passado é do cliente passado
- [X] Bug na soma dos itens 