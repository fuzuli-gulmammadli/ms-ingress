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

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<MicroServiceException> handleEmailAlreadyUsed(EmailAlreadyInUseException emailAlreadyInUseException) {

        MicroServiceException microServiceException = new MicroServiceException();
        microServiceException.setTimestamp(LocalDateTime.now());
        microServiceException.setStatus(HttpStatus.BAD_REQUEST.value());
        microServiceException.setError(HttpStatus.BAD_REQUEST);
        microServiceException.setMessage("Argument validation failed");

        List<Map<String, String>> errors = List.of(
                Map.of("property", "email",
                        "message", "Email already exists")
        );
        microServiceException.setErrors(errors);
        microServiceException.setPath(emailAlreadyInUseException.getPath());

        return new ResponseEntity<>(microServiceException, microServiceException.getError());

    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<MicroServiceException> handlePasswordsNotMatching(PasswordsDoNotMatchException passwordsDoNotMatchException) {

        MicroServiceException microServiceException = new MicroServiceException();
        microServiceException.setTimestamp(LocalDateTime.now());
        microServiceException.setStatus(HttpStatus.BAD_REQUEST.value());
        microServiceException.setError(HttpStatus.BAD_REQUEST);
        microServiceException.setMessage("Argument validation failed");

        List<Map<String, String>> errors = List.of(
                Map.of("property", "password",
                        "message", "Password do not match")
        );
        microServiceException.setErrors(errors);
        microServiceException.setPath(passwordsDoNotMatchException.getPath());

        return new ResponseEntity<>(microServiceException, microServiceException.getError());

    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<MicroServiceException> handleInvalidPassword(InvalidPasswordException invalidPasswordException) {

        MicroServiceException microServiceException = new MicroServiceException();
        microServiceException.setTimestamp(LocalDateTime.now());
        microServiceException.setStatus(HttpStatus.BAD_REQUEST.value());
        microServiceException.setError(HttpStatus.BAD_REQUEST);
        microServiceException.setMessage("Argument validation failed");

        List<Map<String, String>> errors = List.of(
                Map.of("property", "password",
                        "message", "size must be between 8 and 30")
        );
        microServiceException.setErrors(errors);
        microServiceException.setPath(invalidPasswordException.getPath());

        return new ResponseEntity<>(microServiceException, microServiceException.getError());

    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<MicroServiceException> handleEmptyInput(EmptyInputException emptyInputException) {

        MicroServiceException microServiceException = new MicroServiceException();
        microServiceException.setTimestamp(LocalDateTime.now());
        microServiceException.setStatus(HttpStatus.BAD_REQUEST.value());
        microServiceException.setError(HttpStatus.BAD_REQUEST);
        microServiceException.setMessage("Argument validation failed");

        List<Map<String, String>> errors = List.of(
                Map.of("property", emptyInputException.getInputName(),
                        "message", "must not be null")
        );
        microServiceException.setErrors(errors);
        microServiceException.setPath(emptyInputException.getPath());

        return new ResponseEntity<>(microServiceException, microServiceException.getError());

    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<MicroServiceException> handleInvalidEmail(InvalidEmailFormatException invalidEmailFormatException) {

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
