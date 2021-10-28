package ingress.fuzuli.gulmamadli.microservice.service;

import ingress.fuzuli.gulmamadli.microservice.dto.MicroServiceUserDto;
import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.entity.MicroServiceUser;
import ingress.fuzuli.gulmamadli.microservice.exception.EmailAlreadyInUseException;
import ingress.fuzuli.gulmamadli.microservice.exception.EmptyInputException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidEmailFormatException;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidPasswordException;
import ingress.fuzuli.gulmamadli.microservice.exception.PasswordsDoNotMatchException;
import ingress.fuzuli.gulmamadli.microservice.repository.MicroServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MicroServiceUserRepository microServiceUserRepository;
    private final ModelMapper mapper;

    public MicroServiceUserDto signUpUser(SignUpDto signUpDto){

        //control
        if(signUpDto.getEmail() == null || signUpDto.getEmail().equals("")){
            throw new EmptyInputException("/v1/auth/sign-up", "email");
        }else if (signUpDto.getPassword() == null || signUpDto.getPassword().equals("")) {
            throw new EmptyInputException("/v1/auth/sign-up", "password");
        }else if (signUpDto.getPasswordConf() == null || signUpDto.getPasswordConf().equals("")) {
            throw new EmptyInputException("/v1/auth/sign-up", "passwordConf");
        }else if (signUpDto.getPrivacyPolicy() == null) {
            throw new EmptyInputException("/v1/auth/sign-up", "privacyPolicy");
        }

        if(!signUpDto.getEmail().contains("@")){
            throw new InvalidEmailFormatException("/v1/auth/sign-up");
        }

        if(signUpDto.getPassword().length() < 8 || signUpDto.getPassword().length() > 30) {
            throw new InvalidPasswordException("/v1/auth/sign-up");
        }

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConf())) {
            throw new PasswordsDoNotMatchException("/v1/auth/sign-up");
        }

        boolean duplicate = microServiceUserRepository.findAll().stream().map(msu -> msu.getEmail()).anyMatch(email -> email.equals(signUpDto.getEmail()));
        if(duplicate){
            throw new EmailAlreadyInUseException("/v1/auth/sign-up");
        }

        MicroServiceUserDto microServiceUserDto = new MicroServiceUserDto();
        microServiceUserDto.setEmail(signUpDto.getEmail());
        microServiceUserDto.setPassword(signUpDto.getPassword());
        microServiceUserRepository.save(mapper.map(microServiceUserDto, MicroServiceUser.class));
        return microServiceUserDto;
    }

}
