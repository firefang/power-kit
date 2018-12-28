package io.github.firefang.power.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.firefang.power.exception.BadRequestException;
import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.exception.NoPermissionException;
import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.web.CommonResponse;

/**
 * Global exception handler for controllers
 * 
 * @author xinufo
 *
 */
@RestControllerAdvice
public class GlobalExcptionHandler extends ResponseEntityExceptionHandler {
    protected static final Logger LOG = LoggerFactory.getLogger(GlobalExcptionHandler.class);

    /**
     * 处理认证失败异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> handle(UnAuthorizedException ex) {
        return ExceptionHandleUtil.handle(ex);
    }

    /**
     * 处理无权操作异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Void> handle(NoPermissionException ex) {
        return ExceptionHandleUtil.handle(ex);
    }

    /**
     * 处理业务异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleBusinessException(BusinessException ex) {
        return ExceptionHandleUtil.handle(ex, LOG);
    }

    /**
     * 处理无效请求异常
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<Void>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return ExceptionHandleUtil.handle(ex);
    }

    /**
     * 处理未知异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handle(Exception ex) {
        return ExceptionHandleUtil.handleUnknownException(ex, LOG);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String name = ex.getVariableName();
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.INTERNAL_ERROR, "缺少路径参数: " + name);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = ex.getParameterName();
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, "缺少请求参数: " + name);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, "请求绑定异常");
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("conversion not supported", ex);
        return super.handleConversionNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String name = ex.getRequiredType().getSimpleName();
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, "参数类型错误，要求类型: " + name);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("http message not writable", ex);
        return super.handleHttpMessageNotWritable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        return handleBindingResult(result, ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = ex.getRequestPartName();
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, "缺少请求参数: " + name);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        BindingResult result = ex.getBindingResult();
        return handleBindingResult(result, ex, headers, status, request);
    }

    private ResponseEntity<Object> handleBindingResult(BindingResult result, Exception ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return ExceptionHandleUtil.handle(result, ex);
    }

}
