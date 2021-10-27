package ingress.fuzuli.gulmamadli.microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvalidEmailFormatException extends RuntimeException {
    private String path;
}
