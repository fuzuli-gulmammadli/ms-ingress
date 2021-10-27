package ingress.fuzuli.gulmamadli.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirmation;

    @NotNull
    private Boolean acceptsPrivacyPolicy;
}
