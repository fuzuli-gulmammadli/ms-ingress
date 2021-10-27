package ingress.fuzuli.gulmamadli.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class MicroServiceControllerAdvice {

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<MicroServiceException> handleError(InvalidEmailFormatException invalidEmailFormatException) {

        MicroServiceException microServiceException = new MicroServiceException();
        microServiceException.setTimestamp(LocalDateTime.now());
        microServiceException.setStatus(HttpStatus.BAD_REQUEST.value());
        microServiceException.setError(HttpStatus.BAD_REQUEST);
        microServiceException.setMessage("Argument validation failed");

        List<Map<String, String>> errors = List.of(
                Map.of("property", "email",
                        "message", "must be a well-formed email address")
        );
        microServiceException.setErrors(errors);
        microServiceException.setPath(invalidEmailFormatException.getPath());

        return new ResponseEntity<>(microServiceException, microServiceException.getError());

    }

}
