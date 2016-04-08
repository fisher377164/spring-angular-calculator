package com.techmix.fisher.web;

import com.techmix.fisher.dto.ErrorDTO;
import com.techmix.fisher.exeptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author fisher
 * @since 4/8/16
 */

@ControllerAdvice
public class ExceptionTranslator {

    private static final String ERR_METHOD_NOT_SUPPORTED = "error.methodNotSupported";
    private static final String ERR_VALIDATION = "error.validation";

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity processCustomError(BaseException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getStatus().getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, ex.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorDTO(ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }
    private ErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ErrorDTO dto = new ErrorDTO(ERR_VALIDATION);
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }

        return dto;
    }

}
