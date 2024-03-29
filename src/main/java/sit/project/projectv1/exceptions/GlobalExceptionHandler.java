package sit.project.projectv1.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Attributes validation failed",
                webRequest.getDescription(false).substring(4));
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.addValidationError(fieldName, errorMessage);
        });
        System.out.println(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ResponseStatusValidationException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getStatusCode().value(),
                "Attributes validation failed!",
                webRequest.getDescription(false)
        );
        errorResponse.addValidationError(exception.getFieldName(), exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Attributes validation failed",
                webRequest.getDescription(false).substring(4)
        );
        errorResponse.addValidationError(exception.getReason(), exception.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Attributes validation failed",
                webRequest.getDescription(false).substring(4)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlerNullPointerException(NullPointerException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Attributes validation failed",
                webRequest.getDescription(false).substring(4)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlerItemNotFoundException(ItemNotFoundException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                webRequest.getDescription(false).substring(4)
        );
        errorResponse.addValidationError(webRequest.getDescription(false), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlerUnauthorized(HttpClientErrorException.Unauthorized exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
                exception.getMessage(),
                webRequest.getDescription(false).substring(4)
        );
        errorResponse.addValidationError(webRequest.getDescription(false), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handlerAccessDenied(AccessDeniedException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                webRequest.getDescription(false).substring(4)
        );
        errorResponse.addValidationError(webRequest.getDescription(false), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}

