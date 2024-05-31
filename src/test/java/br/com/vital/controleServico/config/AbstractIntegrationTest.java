package br.com.vital.controleServico.config;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
@ExtendWith({PostgreSQLCleaner.class, OutputCaptureExtension.class})
@ContextConfiguration(
        initializers = {PostgreSQLTestContainerConfiguration.EnvInitializer.class}
)
@ActiveProfiles({"test"})
public abstract class AbstractIntegrationTest {
    public AbstractIntegrationTest() {
    }
}
