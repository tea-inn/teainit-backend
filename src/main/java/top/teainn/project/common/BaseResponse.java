package top.teainn.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author teainn
 * @date 2023/11/03 9:14
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer errCode;

    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 数据
     */
    private T data;


    public BaseResponse(Integer errCode, T data, String errMsg) {
        this.errCode = errCode;
        this.data = data;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "{" +
                "errCode=" + errCode +
                ", data=" + data +
                ", msg='" + errMsg + '\'' +
                '}';
    }
}
