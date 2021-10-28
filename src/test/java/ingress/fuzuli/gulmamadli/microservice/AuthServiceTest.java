package ingress.fuzuli.gulmamadli.microservice;

import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.entity.MicroServiceUser;
import ingress.fuzuli.gulmamadli.microservice.exception.EmailAlreadyInUseException;
import ingress.fuzuli.gulmamadli.microservice.exception.EmptyInputException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidEmailFormatException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidPasswordException;
import ingress.fuzuli.gulmamadli.microservice.exception.PasswordsDoNotMatchException;
import ingress.fuzuli.gulmamadli.microservice.repository.MicroServiceUserRepository;
import ingress.fuzuli.gulmamadli.microservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private MicroServiceUserRepository microServiceUserRepository;

    @BeforeEach
    public void init(){
        authService = new AuthService(
                microServiceUserRepository,
                new ModelMapper()
        );
    }

    @Test
    public void whenEmptyInputThenException(){
        //Arrange
        //no email
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(EmptyInputException.class);

        //no password
        signUpDto.setEmail("fuzuli@mail.com");
        signUpDto.setPassword(null);
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(EmptyInputException.class);

        //no password confirmation
        signUpDto.setEmail("fuzuli@mail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf(null);
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(EmptyInputException.class);

        //no privacy
        signUpDto.setEmail("fuzuli@mail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(null);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(EmptyInputException.class);
    }

    @Test
    public void whenInvalidEmailThenException() {

        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("admin");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(InvalidEmailFormatException.class);
    }

    @Test
    public void whenInvalidPasswordThenException() {
        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fuzuli.gulmammadli@gmail.com");
        signUpDto.setPassword("123456");
        signUpDto.setPasswordConf("123456");
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    public void whenPasswordsDoNotMatchThenException() {
        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fuzuli.gulmammadli@gmail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345679");
        signUpDto.setPrivacyPolicy(true);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(PasswordsDoNotMatchException.class);
    }

    @Test
    public void whenEmailAlreadyUsedThenException() {
        //Arrange
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("fuzuli.gulmammadli@gmail.com");
        signUpDto.setPassword("12345678");
        signUpDto.setPasswordConf("12345678");
        signUpDto.setPrivacyPolicy(true);

        var microServiceUsers = List.of(
                new MicroServiceUser(
                        1L,
                        "fuzuli.gulmammadli@gmail.com",
                        "12345678"
                )
        );

        when(microServiceUserRepository.findAll()).thenReturn(microServiceUsers);

        //Act and Assert
        assertThatThrownBy(() -> authService.signUpUser(signUpDto))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }

}
