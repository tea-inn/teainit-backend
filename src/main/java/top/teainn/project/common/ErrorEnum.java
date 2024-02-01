package top.teainn.project.common;

import lombok.Getter;

/**
 * 错误码
 *
 * @author teainn
 * @date 2023/11/03 9:24
 */
@Getter
public enum ErrorEnum {
    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 请求参数错误
     * 参数为空，格式错误等
     */
    PARAMS_ERROR(40000, "请求参数错误"),

    /**
     * 账号已存在
     */
    USER_ACCOUNT_ALREADY_EXIST(40001, "账号已存在"),

    /**
     * 没有登录或者登录过期
     */
    NOT_LOGIN_ERROR(40100, "未登录或登录已过期"),

    /**
     * 无权限访问，禁止访问该资源
     */
    NO_AUTH_ERROR(40300, "权限不足"),

    /**
     * 查询数据库没有数据
     */
    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    /**
     * 系统内部各种异常（如超时，查询数据库失败）
     */
    SYSTEM_ERROR(50000, "系统内部异常，请稍后重试");

    /**
     * 错误码
     */
    private final Integer errCode;

    /**
     * 错误信息
     */
    private final String errMsg;

    ErrorEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

}
