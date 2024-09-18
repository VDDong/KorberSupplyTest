package KorberSupply.PruebaTecnica;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import KorberSupply.PruebaTecnica.POJO.ErrorHandlingResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandlingResponse> handleGlobalException(Exception ex, WebRequest request) {
		ErrorHandlingResponse errorResponse = new ErrorHandlingResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorHandlingResponse> handleNoSuchElementException(Exception ex, WebRequest request) {
		ErrorHandlingResponse errorResponse = new ErrorHandlingResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorHandlingResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {
		ErrorHandlingResponse errorResponse = new ErrorHandlingResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
				);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}