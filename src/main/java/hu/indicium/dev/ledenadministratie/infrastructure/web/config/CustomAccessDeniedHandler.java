package hu.indicium.dev.ledenadministratie.infrastructure.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.util.ErrorDto;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Access denied");

        Response<ErrorDto> error = ResponseBuilder.forbidden()
                .error(errorDto)
                .build();

        String body = objectMapper.writeValueAsString(error);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(body);
    }
}
