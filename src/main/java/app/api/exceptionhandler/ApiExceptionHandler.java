package app.api.exceptionhandler;

import app.exception.EntidadeNotFoundException;
import app.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /* Manipula Exception do Sistema */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest webRequest) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitulo(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());

        return this.handleExceptionInternal(ex, problema, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(EntidadeNotFoundException.class)
    public ResponseEntity<Object> handleEntidadeNotFoundException(NegocioException ex, WebRequest webRequest) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitulo(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());

        return this.handleExceptionInternal(ex, problema, new HttpHeaders(), status, webRequest);
    }

    /* Manipula Exception do Bean Validation, onde foi utilizada o @Valid no argumento dos métodos */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Problema.Campo> campos = new ArrayList<>();

        for(ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String campo = ((FieldError) objectError).getField();
            //String msg = objectError.getDefaultMessage(); // Pega a mensagem configurada no BeanValidation, atributo 'messages'
            String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            campos.add(new Problema.Campo(campo, msg));
        }

        Problema problema = new Problema();

        problema.setStatus(status.value());
        problema.setTitulo("Um ou mais campos inválidos");
        problema.setDataHora(OffsetDateTime.now());
        problema.setCampos(campos);

        return super.handleExceptionInternal(ex, problema, headers, status, request);

    }
}
