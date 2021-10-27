package ingress.fuzuli.gulmamadli.microservice.controller;

import ingress.fuzuli.gulmamadli.microservice.dto.MicroServiceUserDto;
import ingress.fuzuli.gulmamadli.microservice.dto.SignUpDto;
import ingress.fuzuli.gulmamadli.microservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public MicroServiceUserDto signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return authService.signUpUser(signUpDto);
    }
}
