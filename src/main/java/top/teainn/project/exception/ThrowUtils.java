package top.teainn.project.exception;

import top.teainn.project.common.ErrorEnum;

/**
 * 异常抛出工具类
 *
 * @author teainn
 * @date 2024/01/10 12:30
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorEnum 错误码枚举
     */
    public static void throwIf(boolean condition, ErrorEnum errorEnum) {
        throwIf(condition, new BusinessException(errorEnum));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorEnum 错误码枚举
     * @param errMsg 自定义错误信息
     */
    public static void throwIf(boolean condition, ErrorEnum errorEnum, String errMsg) {
        throwIf(condition, new BusinessException(errorEnum, errMsg));
    }
}
