package ru.firstproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.firstproject.util.exception.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import static ru.firstproject.util.exception.ErrorType.VALIDATION_ERROR;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler  {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

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
    @ExceptionHandler(VoteAlreadyStarted.class)
    public ErrorInfo handleChangeDenyError(HttpServletRequest req, VoteAlreadyStarted e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.VOTE_ALREADY_STARTED);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(TimesUpException.class)
    public ErrorInfo handleTimeUpError(HttpServletRequest req, TimesUpException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.TIMES_UP);
    }

/*    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Throwable.class)
    public ErrorInfo handleAccessDenied(HttpServletRequest req, AccessDeniedException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.USER_UNAUTHORIZED);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorInfo handleAccessForbidden(HttpServletRequest req, AccessDeniedException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.USER_FORBIDDEN);
    }*/
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo handleValidationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
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
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), e);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), e.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, e.toString());
    }
}