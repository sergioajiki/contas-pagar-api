package projetos.contas_pagar_api.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projetos.contas_pagar_api.advice.exception.DuplicateEntryException;
import projetos.contas_pagar_api.advice.exception.InvalidDataException;
import projetos.contas_pagar_api.advice.exception.InvalidEmailFormatException;
import projetos.contas_pagar_api.advice.exception.NotFoundException;
import projetos.contas_pagar_api.dto.ErrorMesssageDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralControllerAdvice {
    private final MessageSource messageSource;

    @Autowired
    public GeneralControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException exception) {
        Problem problem = new Problem(
                HttpStatus.BAD_REQUEST.value(),
                "Informação não encontrada",
                exception.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleDuplicateEntryExcpeiton(DuplicateEntryException exception) {
        Problem problem = new Problem(
                HttpStatus.CONFLICT.value(),
                "Informações de entrada duplicadas",
                exception.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleFieldNotFound(MethodArgumentNotValidException exception) {
        List<ErrorMesssageDto> problemList = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(e -> {
            String detail = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorMesssageDto messageDetail = new ErrorMesssageDto(
                    e.getField(),
                    detail
            );
            problemList.add(messageDetail);
        });

        Problem problem = new Problem(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Parameters",
                "Invalid Request Body",
                problemList
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleInvalidEmailFormat(InvalidEmailFormatException exception) {
        Problem problem = new Problem(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Email Format",
                exception.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleInvalidData(InvalidDataException exception) {
        Problem problem = new Problem(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid or missing data",
                exception.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Problem> handleConstraintViolationException(ConstraintViolationException exception) {
        Problem problem = new Problem(
                HttpStatus.FORBIDDEN.value(),
                "Constrain violation",
                exception.getConstraintViolations().stream().toList().toString(),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
    }
}
