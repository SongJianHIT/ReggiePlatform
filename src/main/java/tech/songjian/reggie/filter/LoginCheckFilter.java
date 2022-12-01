/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.filter
 * @className tech.songjian.reggie.filter.LoginCheckFilter
 */
package tech.songjian.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import tech.songjian.reggie.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginCheckFilter
 * @description 检查是否登入过滤器
 * @author SongJian
 * @date 2022/12/1 21:55
 * @version
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // 路径比较器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取本次请求的 uri
        String requestURI = request.getRequestURI();

        log.info("拦截到路径：{}", requestURI);

        // 定义一些不需要拦截的请求，直接放行
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        // 2、判断本次请求是否需要处理
        boolean check = check(requestURI, urls);

        // 3、如果不需要处理，直接放行
        if (check) {
            log.info("本次请求不需要处理：{}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 4、判断登入状态，如果已登入，则放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登入，用户id为：{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        // 5、如果没有登入，则返回未登入结果
        // 通过输出流方式，向客户端页面响应数据
        log.info("用户未登入");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * @title check
     * @author SongJian
     * @param: requestURI 需要进行匹配的uri
     * @param: urls 可以放行的资源数组
     * @updateTime 2022/12/1 22:12
     * @return: boolean
     * @throws
     * @description 路径匹配，检查本次请求是否能够放行
     */
    public boolean check(String requestURI, String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}