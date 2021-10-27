package ingress.fuzuli.gulmamadli.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroServiceUserDto {
    private Long id;
    private String email;
    private String password;
}
