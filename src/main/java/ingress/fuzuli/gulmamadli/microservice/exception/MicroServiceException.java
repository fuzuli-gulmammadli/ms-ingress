package ingress.fuzuli.gulmamadli.microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroServiceException {
    private LocalDateTime timestamp;
    private int status;
    private HttpStatus error;
    private String message;
    private String path;
    List<Map<String, String>> errors;
}
