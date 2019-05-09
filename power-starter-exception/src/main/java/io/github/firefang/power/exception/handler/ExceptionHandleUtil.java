package io.github.firefang.power.exception.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import io.github.firefang.power.exception.BadRequestException;
import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.exception.NoPermissionException;
import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.web.CommonResponse;

/**
 * 异常处理工具类
 * 
 * @author xinufo
 *
 */
public class ExceptionHandleUtil {

    private ExceptionHandleUtil() {
    }

    public static ResponseEntity<CommonResponse<Void>> handle(UnAuthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.fail(CommonResponse.UNAUTHORIZED, ex.getMessage()));
    }

    public static ResponseEntity<Void> handle(NoPermissionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public static ResponseEntity<CommonResponse<Void>> handle(BusinessException ex, Logger log) {
        String reason = "操作失败, " + ex.getMessage();

        if (log.isWarnEnabled()) {
            log.warn("业务异常: " + reason);
        }

        CommonResponse<Void> body = CommonResponse.fail(CommonResponse.BUSINESS_FAIL, reason);
        return ResponseEntity.ok().body(body);
    }

    public static ResponseEntity<CommonResponse<Void>> handle(BadRequestException ex) {
        CommonResponse<Void> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    public static ResponseEntity<CommonResponse<Void>> handleUnknownException(Exception ex, Logger log) {
        log.error("未知异常", ex);
        CommonResponse<Void> body = CommonResponse.fail(CommonResponse.INTERNAL_ERROR, "未知异常");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public static ResponseEntity<Object> handle(BindingResult result, Exception ex) {
        Map<String, Object> infos = new HashMap<>(16);
        if (result.hasGlobalErrors()) {
            List<String> globals = new LinkedList<>();
            infos.put("global", globals);
            for (ObjectError err : result.getGlobalErrors()) {
                globals.add(err.getDefaultMessage());
            }
        }
        for (FieldError err : result.getFieldErrors()) {
            infos.put(err.getField(), err.getDefaultMessage());
        }
        CommonResponse<?> body = CommonResponse.fail(CommonResponse.BAD_REQUEST, "参数校验不通过").data(infos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
