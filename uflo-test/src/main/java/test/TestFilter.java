package test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jacky.gao
 * @since 2016年12月9日
 */
public class TestFilter implements Filter{
	public static final String LOGIN_USERNAME="loginUsername";
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		RequestHolder.setRequest(req);
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
			req.getSession().setAttribute(LOGIN_USERNAME, username);
			return;
		}
		String uri=req.getRequestURI();
		boolean ok=false;
		if(req.getSession().getAttribute(LOGIN_USERNAME)==null){
			if (uri.equals("/uflo-test/")
					|| uri.equals("/uflo-test/index.html")
					|| uri.equals("/uflo-test/doLogin")
					|| uri.equals("/uflo-test/uflo/deploy")
					|| uri.equals("/uflo-test/uflo/assignproviderlist")
					|| uri.equals("/uflo-test/uflo/handlerlist")
					|| uri.endsWith(".js") || uri.endsWith(".css")
					|| uri.endsWith(".jpg") || uri.endsWith(".png")) {
				ok=true;
			}else{
				ok=false;
			}
		}else{
			ok=true;
		}
		if(!ok){
			HttpServletResponse resp=(HttpServletResponse)response;
			resp.sendRedirect(req.getContextPath()+"/index.html");
			return;
		}
		try{
			chain.doFilter(request, response);			
		}finally{
			RequestHolder.remove();
		}
	}

	public void destroy() {
	}
}
