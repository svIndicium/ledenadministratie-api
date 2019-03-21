package hu.indicium.dev.lit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Application")
@Tag("Application")
class LitApplicationTests {

    @Test
    @DisplayName("Test context loading")
    void contextLoads() {
    }

    @Test
    @DisplayName("Test startup")
    void main() {
        LitApplication.main(new String[]{});
        assertThat(true).isTrue();
    }

}
