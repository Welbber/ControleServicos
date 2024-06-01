package br.com.vital.controleServico.customers;


import br.com.vital.controleServico.common.controller.ApiError;
import br.com.vital.controleServico.config.AbstractIntegrationTest;
import br.com.vital.controleServico.config.MockHTTPConverter;
import br.com.vital.controleServico.customers.dto.AddressDTO;
import br.com.vital.controleServico.customers.dto.CustomerDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc()
@Sql("/db/customers/insert_customers.sql")
class CustomersControllerIT extends AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockHTTPConverter mockHTTPConverter;


    @Test
    @Disabled
    void getAllCustomersTest() throws Exception {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "customers"))
                .isEqualTo(2);

        var response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        final var expectedResponse = List.of(
                new CustomerDTO(1L,
                        "João Silva",
                        "joao@example.com",
                        "01263538088",
                        "6122277193",
                        new AddressDTO(
                                1L,
                                "Rua das Flores, 123",
                                12902,
                                "SALA 3",
                                "São Paulo",
                                "SP",
                                "centro",
                                "12345-678")),
                new CustomerDTO(2L,
                        "Maria Oliveira",
                        "maria@example.com",
                        "86776470073",
                        "6172277198",
                        new AddressDTO(
                                2L,
                                "Rua dos Pinheiros, 200",
                                12902, "SALA 3",
                                "Rio de Janeiro",
                                "RJ",
                                "CENTRO",
                                "87654-321"))
        );

        final var responseContent = mockHTTPConverter.convertJsonResponse(response, PageImpl.class);
        assertThat(responseContent.getTotalElements())
                .isEqualTo(2);
    }


    @Test
    void getCustomerByIdTest() throws Exception {
        final var id = 1L;
        var response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/customers/%s".formatted(id))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();

        final var expectedResponse = new CustomerDTO(1L,
                "João Silva",
                "joao@example.com",
                "01263538088",
                "6122277193",
                new AddressDTO(
                        1L,
                        "Rua das Flores, 123",
                        12902,
                        "SALA 3",
                        "São Paulo",
                        "SP",
                        "CENTRO",
                        "12345-678"));
        final var responseContent = mockHTTPConverter.convertJsonResponse(response, CustomerDTO.class);
        assertThat(responseContent).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void getCustomerByIdNotFoundTest() throws Exception {
        final var id = 9999L;
        var response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/customers/%s".formatted(id))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn().getResponse();
        final var responseContent = mockHTTPConverter.convertJsonResponse(response, ApiError.class);
        final var expectedResponse = new ApiError(HttpStatus.NOT_FOUND, "Customer not found.");
        assertThat(responseContent).usingRecursiveComparison().isEqualTo(expectedResponse);
    }


//    @Test
//    void insertCustomerValidTest() throws Exception {
//        final var customer = new CustomerDTO(null,
//                "João Silva",
//                "joao@example.com",
//                "01263538088",
//                "6122277193",
//                new AddressDTO(
//                        1L,
//                        "Rua das Flores, 123",
//                        12902,
//                        "SALA 3",
//                        "São Paulo",
//                        "SP",
//                        "centro",
//                        "12345-678"));
//
//        var response = mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/customers/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("""
//                                        { "name":  "Test", "email":  "test@gmail.com"}
//                                        """))
//                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn().getResponse();
//
//        final var responseContent = mockHTTPConverter.convertJsonResponse(response, CustomerDTO.class);
//
//        Assertions.assertThat(responseContent)
//                .usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(customer);
//
//        final var customerDB = jdbcTemplate.queryForMap("select * from customers where email = '%s'".formatted(customer.email()));
//        Assertions.assertThat(customerDB)
//                .containsEntry("id", 1L)
//                .containsEntry("name", customer.name())
//                .containsEntry("email", customer.email());
//    }
//
//    @Sql("/db/customers/insert_customers.sql")
//    @Test
//    void insertCustomerAlreadyExistsValidTest() throws Exception {
//        final var customer = new Customer("Test", "test@gmail.com");
//
//        var response = mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/customers/")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("""
//                                        { "name":  "Test", "email":  "test@gmail.com"}
//                                        """))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse();
//
//        final var expectedResponse = "Customer %s already exists.".formatted(customer.getEmail());
//        Assertions.assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
//        Assertions.assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "customers", "email = '%s'".formatted(customer.getEmail())))
//                .isEqualTo(1);
//    }


}
