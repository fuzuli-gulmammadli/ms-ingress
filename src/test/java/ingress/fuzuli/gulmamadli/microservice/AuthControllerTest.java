package ingress.fuzuli.gulmamadli.microservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ingress.fuzuli.gulmamadli.microservice.controller.AuthController;
import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.exception.EmptyInputException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidEmailFormatException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidPasswordException;
import ingress.fuzuli.gulmamadli.microservice.exception.PasswordsDoNotMatchException;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        EmptyInputException emptyInputException = new EmptyInputException("/v1/auth/sign-up", "email");
        when(authService.signUpUser(signUpDto)).thenThrow(emptyInputException);

        //Act
        mockMvc.perform(
                post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("must not be null")));
    }

    @Test
    void whenInvalidEmailThenBadRequest() throws Exception {

        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("invalid-email");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        InvalidEmailFormatException invalidEmailFormatException = new InvalidEmailFormatException("/v1/auth/sign-up");
        when(authService.signUpUser(signUpDto)).thenThrow(invalidEmailFormatException);

        //Act
        mockMvc.perform(
                post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("must be a well-formed email address")));
    }

    @Test
    void whenInvalidPasswordThenBadRequest() throws Exception {

        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fuzuli@mail.com");
        signUpDto.setPassword("12345");
        signUpDto.setPasswordConf("12345");
        signUpDto.setPrivacyPolicy(true);

        InvalidPasswordException invalidPasswordException = new InvalidPasswordException("/v1/auth/sign-up");
        when(authService.signUpUser(signUpDto)).thenThrow(invalidPasswordException);

        //Act
        mockMvc.perform(
                post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("size must be between 8 and 30")));
    }

    @Test
    void whenPasswordsDoNotMatchThenBadRequest() throws Exception {

        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fuzuli@mail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345679");
        signUpDto.setPrivacyPolicy(true);

        PasswordsDoNotMatchException passwordsDoNotMatchException = new PasswordsDoNotMatchException("/v1/auth/sign-up");
        when(authService.signUpUser(signUpDto)).thenThrow(passwordsDoNotMatchException);

        //Act
        mockMvc.perform(
                post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
        ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("Password do not match")));
    }

}
