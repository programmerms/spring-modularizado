package br.com.itau.tailor.exception;

import br.com.itau.tailor.example.ExampleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldHandleGenericException() throws Exception {
        mockMvc.perform(get("/api/tailor/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Erro interno"))
                .andExpect(jsonPath("$.message", containsString("generic error")));
    }

    @Test
    void shouldHandleNotFoundException() throws Exception {
        mockMvc.perform(get("/api/tailor/notfound"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.message").value("not found"));
    }

    @Test
    void shouldHandleBusinessException() throws Exception {
        mockMvc.perform(get("/api/tailor/business"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de regra de negócio"))
                .andExpect(jsonPath("$.message").value("business rule violated"));
    }

    @Test
    void shouldHandleAccessDenied() throws Exception {
        mockMvc.perform(get("/api/tailor/forbidden"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Acesso negado"))
                .andExpect(jsonPath("$.message").value("no access"));
    }
}
