package ru.firstproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.firstproject.util.exception.*;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleNotFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ChangeDeniedException.class)
    public ErrorInfo handleChangeDenyError(HttpServletRequest req, ChangeDeniedException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.CHANGE_DENY);
    }
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(TimesUpException.class)
    public ErrorInfo handleTimeUpError(HttpServletRequest req, TimesUpException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.TIMES_UP);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(MenuNotReadyException.class)
    public ErrorInfo handleMenuError(HttpServletRequest req, MenuNotReadyException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.MENU_NOT_READY);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.APP_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
//        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), e);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), e.toString());
        }

        return new ErrorInfo(req.getRequestURL(), errorType, e.toString());
    }
}