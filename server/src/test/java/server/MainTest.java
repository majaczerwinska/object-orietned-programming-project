package server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class MainTest {

    @Test
    public void contextActivation() {
        String[] args = {};
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = app.run(args);

        // Verify that the Spring context is loaded without errors
        Assertions.assertTrue(context.isActive());
    }

    @Test
    public void mainMethodTest() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        System.exit(0);
//        try {
//            Main.main(new String[]{});
//        } catch (Exception e) {
//            Assertions.fail("Exception thrown: " + e.getMessage());
//        }
    }
}