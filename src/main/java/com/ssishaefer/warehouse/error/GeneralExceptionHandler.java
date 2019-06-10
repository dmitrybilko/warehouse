package com.ssishaefer.warehouse.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import com.ssishaefer.warehouse.exception.ArticleAlreadyExistsException;
import com.ssishaefer.warehouse.exception.ArticleNotFoundException;
import com.ssishaefer.warehouse.exception.LocationAlreadyExistsException;
import com.ssishaefer.warehouse.exception.LocationNotFoundException;
import com.ssishaefer.warehouse.exception.StockItemAlreadyExistsException;
import com.ssishaefer.warehouse.exception.StockItemNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    public ResponseEntity<Object> handleArticleAlreadyExistsError(
        final ArticleAlreadyExistsException e, final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Article Already Exists",
            "Article '" + e.getName() + "' already exists: " + e.getId(), request);
    }

    @ExceptionHandler(LocationAlreadyExistsException.class)
    public ResponseEntity<Object> handleLocationAlreadyExistsError(
        final LocationAlreadyExistsException e, final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Location Already Exists",
            "Location '" + e.getName() + "' already exists: " + e.getId(), request);
    }

    @ExceptionHandler(StockItemAlreadyExistsException.class)
    public ResponseEntity<Object> handleStockItemAlreadyExistsError(
        final StockItemAlreadyExistsException e, final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Stock Item Already Exists",
            "Stock item for '" + e.getArticle() + "' in '" + e.getLocation() + "' already exists: "
                + e.getId(), request);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Object> handleArticleNotFoundError(final ArticleNotFoundException e,
        final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Article Not Found",
            "Unable to find article: " + e.getId(), request);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Object> handleLocationNotFoundError(final LocationNotFoundException e,
        final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Location Not Found",
            "Unable to find location: " + e.getId(), request);
    }

    @ExceptionHandler(StockItemNotFoundException.class)
    public ResponseEntity<Object> handleStockItemNotFoundError(final StockItemNotFoundException e,
        final WebRequest request) {
        return handleGeneralError(e, BAD_REQUEST, "Stock Item Not Found",
            "Unable to find stock item: " + e.getId(), request);
    }

    @SuppressWarnings("SameParameterValue")
    private ResponseEntity<Object> handleGeneralError(final Exception e, final HttpStatus status,
        final String title, final String details, final WebRequest request) {
        log.error(details);
        return handleExceptionInternal(e, GeneralError
            .builder()
            .status(status.value())
            .title(title)
            .details(details)
            .build(), new HttpHeaders(), status, request);
    }
}
