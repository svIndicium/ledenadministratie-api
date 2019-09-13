package hu.indicium.dev.ledenadministratie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Application")
@Tag("Application")
class LedenadministratieApplicationTests {

    @Test
    @DisplayName("Test context loading")
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Test startup")
    void main() {
        LedenadministratieApplication.main(new String[]{});
        assertThat(true).isTrue();
    }

}
