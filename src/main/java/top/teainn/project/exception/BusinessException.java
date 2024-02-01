package top.teainn.project.exception;

import lombok.Getter;
import top.teainn.project.common.ErrorEnum;

/**
 * 业务异常类
 *
 * @author teainn
 * @date 2023/11/03 9:57
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码枚举
     */
    private final ErrorEnum errorEnum;

    /**
     * 错误信息
     */
    private final String errMsg;

    public BusinessException(ErrorEnum errorEnum, String errMsg) {
        this.errorEnum = errorEnum;
        this.errMsg = errMsg;
    }

    public BusinessException(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
        this.errMsg = errorEnum.getErrMsg();
    }

}
