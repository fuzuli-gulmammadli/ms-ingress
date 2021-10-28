package ingress.fuzuli.gulmamadli.microservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingress.fuzuli.gulmamadli.microservice.controller.AuthController;
import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.exception.EmptyInputException;
import ingress.fuzuli.gulmamadli.microservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenEmptyInputThenBadRequest() throws Exception {

        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        EmptyInputException emptyInputException = new EmptyInputException("/v1/auth/sign-up", "email");
        when(authService.signUpUser(signUpDto)).thenThrow(emptyInputException);

        //Act
        mockMvc.perform(
                post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isBadRequest());
    }

}
