package top.teainn.project.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.ResultUtils;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author teainn
 * @date 2023/11/03 9:34
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Void> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getErrorEnum(),e.getErrMsg());
    }

    /**
     * 参数校验未通过异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(ErrorEnum.PARAMS_ERROR.getErrMsg(), message);

        return ResultUtils.error(ErrorEnum.PARAMS_ERROR, message);
    }

    /**
     * 参数校验未通过异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse<Void> bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);
        String message = fieldError.getDefaultMessage();
        return ResultUtils.error(ErrorEnum.PARAMS_ERROR, message);
    }

    /**
     * sa-token 未登录异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> notLoginException(NotLoginException e) {
        log.error(ErrorEnum.NOT_LOGIN_ERROR.getErrMsg(), e.getMessage());
        return ResultUtils.error(ErrorEnum.NOT_LOGIN_ERROR, e.getMessage());
    }

    /**
     * sa-token 未授权异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<?> notPermissionExceptionHandler(NotPermissionException e) {
        log.error(ErrorEnum.NO_AUTH_ERROR.getErrMsg(), e.getMessage());
        return ResultUtils.error(ErrorEnum.NO_AUTH_ERROR, e.getMessage());
    }

    /**
     * 容器错误异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(NestedServletException.class)
    public BaseResponse<?> nestedServletExceptionHandler(NestedServletException e) {
        log.error(ErrorEnum.SYSTEM_ERROR.getErrMsg(), e.getMessage());
        return ResultUtils.error(ErrorEnum.SYSTEM_ERROR, e.getMessage());
    }

    /**
     * 运行时异常处理
     *
     * @param e 异常
     * @return BaseResponse
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<Void> runtimeExceptionHandler(RuntimeException e) {
        log.error(ErrorEnum.SYSTEM_ERROR.getErrMsg(), e);
        return ResultUtils.error(ErrorEnum.SYSTEM_ERROR);
    }
}
