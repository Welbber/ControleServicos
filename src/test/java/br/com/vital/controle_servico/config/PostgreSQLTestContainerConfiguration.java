package br.com.vital.controle_servico.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestContainerConfiguration {

    private static final PostgreSQLContainer<?> POSTGRES = (
            new PostgreSQLContainer("postgres:15-alpine"))
            .withDatabaseName("controle-servico-test")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        POSTGRES.start();
    }

    private PostgreSQLTestContainerConfiguration() {
    }

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        EnvInitializer() {
        }

        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(new String[]{
                            "spring.datasource.url=%s".formatted(
                                    PostgreSQLTestContainerConfiguration.POSTGRES.getJdbcUrl()),
                            "spring.datasource.username=postgres",
                            "spring.datasource.password=postgres"})
                    .applyTo(applicationContext);
        }
    }

}
