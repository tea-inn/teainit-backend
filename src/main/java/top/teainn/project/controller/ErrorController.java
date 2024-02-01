package top.teainn.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.teainn.project.common.BaseResponse;
import top.teainn.project.common.ErrorEnum;
import top.teainn.project.common.ResultUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器异常请求控制类
 *
 * @author teainn
 * @date 2024/01/28 23:21
 */
@RestController
@RequestMapping("/filter")
public class ErrorController {

    /**
     * 重新抛出异常
     *
     * @param request 请求
     * @return BaseResponse<Void>
     */
    @GetMapping("/throw")
    public BaseResponse<Void> exception(HttpServletRequest request) {
        ErrorEnum errorEnum = (ErrorEnum) request.getAttribute("errorCode");
        String msg = request.getAttribute("msg").toString();
        return ResultUtils.error(errorEnum, msg);
    }
}
