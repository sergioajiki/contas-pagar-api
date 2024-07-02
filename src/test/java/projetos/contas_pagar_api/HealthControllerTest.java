package projetos.contas_pagar_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import projetos.contas_pagar_api.controller.HealthController;

@WebMvcTest(HealthController.class)
public class HealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //Este método de teste faz uma solicitação GET ao endpoint /health
    @Test
    public void sayHello_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk()) //Verifica se o status da resposta é 200 OK.
                .andExpect(content().string("Contas a pagar - Success!!!")); //Verifica se o conteúdo da resposta é "Contas a pagar - Success!!!".
    }
}
