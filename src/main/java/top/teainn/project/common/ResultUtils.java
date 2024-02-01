package top.teainn.project.common;

/**
 * 返回工具类
 *
 * @author teainn
 * @date 2023/11/03 9:15
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param <T> 返回数据
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(ErrorEnum.SUCCESS.getErrCode(), null, ErrorEnum.SUCCESS.getErrMsg());
    }

    /**
     * 成功
     *
     * @param data 成功数据返回
     * @param <T>  返回数据
     * @return BaseResponse
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(ErrorEnum.SUCCESS.getErrCode(), data, ErrorEnum.SUCCESS.getErrMsg());
    }

    /**
     * 失败
     *
     * @param errorEnum 错误码枚举
     * @return BaseResponse
     */
    public static BaseResponse<Void> error(ErrorEnum errorEnum) {
        return new BaseResponse<Void>(errorEnum.getErrCode(), null, errorEnum.getErrMsg());
    }

    /**
     * 失败
     *
     * @param errorEnum 错误码枚举
     * @param errMsg    错误信息
     * @return BaseResponse
     */
    public static BaseResponse<Void> error(ErrorEnum errorEnum, String errMsg) {
        return new BaseResponse<Void>(errorEnum.getErrCode(), null, errMsg);
    }

}
