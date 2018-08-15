package service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpMethod;

public class CorsFilter implements Filter {
    private String domainName;
    public void destroy() {
    }
    public CorsFilter(String domainName) {
        this.domainName = domainName;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        if (null == this.domainName || this.domainName.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        res.setHeader("Access-Control-Allow-Origin", this.domainName);
        //res.setHeader("Access-Control-Allow-Origin", "*");
        if (((HttpServletRequest)request).getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            res.setHeader("Access-Control-Max-Age", "1728000");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Authorization");
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(request, res);
    }
    public void init(FilterConfig config) throws ServletException {
    }
}
