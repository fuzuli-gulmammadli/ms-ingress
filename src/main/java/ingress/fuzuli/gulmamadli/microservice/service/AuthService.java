package ingress.fuzuli.gulmamadli.microservice.service;

import ingress.fuzuli.gulmamadli.microservice.dto.MicroServiceUserDto;
import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.entity.MicroServiceUser;
import ingress.fuzuli.gulmamadli.microservice.exception.InvalidEmailFormatException;
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
        if(!signUpDto.getEmail().contains("@")){
            throw new InvalidEmailFormatException("/v1/auth/sign-up");
        }

        MicroServiceUserDto microServiceUserDto = new MicroServiceUserDto();
        microServiceUserDto.setEmail(signUpDto.getEmail());
        microServiceUserDto.setPassword(signUpDto.getPassword());
        microServiceUserRepository.save(mapper.map(microServiceUserDto, MicroServiceUser.class));
        return microServiceUserDto;
    }

}
