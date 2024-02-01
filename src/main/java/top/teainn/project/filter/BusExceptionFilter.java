package top.teainn.project.filter;

import org.springframework.stereotype.Component;
import top.teainn.project.exception.BusinessException;

import javax.servlet.*;
import java.io.IOException;

@Component
public class BusExceptionFilter implements Filter {

    private static final String ERROR_PATH="/filter/throw";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 异常码和异常信息分别赋值给code和message字段
            BusinessException businessException = (BusinessException) e;
            request.setAttribute("errorCode",businessException.getErrorEnum());
            request.setAttribute("errMsg", businessException.getErrMsg());
            //将异常转发到ErrorControl
            request.getRequestDispatcher(ERROR_PATH).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
