package ingress.fuzuli.gulmamadli.microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAlreadyInUseException extends RuntimeException {
    private String path;
}
