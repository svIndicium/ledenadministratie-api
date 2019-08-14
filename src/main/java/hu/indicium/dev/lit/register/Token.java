package hu.indicium.dev.lit.register;

import java.util.Objects;

public class Token {
    private String jwtToken;

    public Token(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(jwtToken, token.jwtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwtToken);
    }
}
