package hu.indicium.dev.ledenadministratie.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {

    @Test
    void getFullLastName() {
        assertThat(Util.getFullLastName("test", "lmao")).isEqualTo("test lmao");
        assertThat(Util.getFullLastName(null, "lmao")).isEqualTo("lmao");
    }
}