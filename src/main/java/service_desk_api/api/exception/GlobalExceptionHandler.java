package service_desk_api.api.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import service_desk_api.api.dto.ApiResponse;
import service_desk_api.api.model.Status;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	//public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		//Map<String, String> errors = new HashMap<>();
		//ex.getBindingResult().getFieldErrors().forEach(error ->
				//errors.put(error.getField(), error.getDefaultMessage()));
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//	public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {	
//		return ResponseEntity
//				.status(HttpStatus.BAD_GATEWAY)
//				.body(ApiResponse.builder()
//						.timestamp(LocalDateTime.now())
//						.status(HttpStatus.BAD_REQUEST.value())
//						.message("Erro de validação: " + ex.getMessage())
//						.data(null)
//						.build()
//						);
//	}
	public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining("; "));
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.error("Erro de validação: " + errors, 400));
	}
	
	@ExceptionHandler(RuntimeException.class)
	//public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
	public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
		//Map<String, String> error = new HashMap<>();
		//error.put("erro", 
				//ex.getMessage().contains("not one of the values accepted for Enum class") ? "O valor do status não é permitido" : ex.getMessage()
				//);
		//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		System.out.println("Erro de Hantaime.");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Erro interno: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<?>> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
		var message = ex.getMostSpecificCause().toString().contains("Status")
				? "O campo status não foi preenchido com o valor correto. Por favor, escolha entre as opções: "
						+ Status.ABERTO + ", " + Status.EM_ANDAMENTO + ", " + Status.CONCLUIDO
				: ex.getMessage();
		System.out.println("Not Readable: ");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Erro de serialização: " + message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
	
//	@ExceptionHandler(ExpiredJwtException.class)
//	public ResponseEntity<ApiResponse<?>> handleExpiredJwtTokenException(ExpiredJwtException ex) {
//		return ResponseEntity
//				.status(HttpStatus.FORBIDDEN)
//				.body(ApiResponse.error("Token expirado: ", HttpStatus.FORBIDDEN.value()));
//	}
//	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
		System.out.println("Erro genericão");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

}
