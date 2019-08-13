package hu.indicium.dev.lit.register.mapper;

import hu.indicium.dev.lit.register.Token;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.util.Mapper;

public class TokenMapper implements Mapper<Token, TokenDTO> {
    @Override
    public Token toEntity(TokenDTO tokenDTO) {
        return new Token(tokenDTO.getToken());
    }

    @Override
    public TokenDTO toDTO(Token token) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token.getJwtToken());
        return tokenDTO;
    }
}
