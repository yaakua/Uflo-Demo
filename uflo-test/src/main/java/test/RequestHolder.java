package test;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jacky.gao
 * @since 2016年12月9日
 */
public class RequestHolder {
	private static final ThreadLocal<HttpServletRequest> req=new ThreadLocal<HttpServletRequest>();
	public static HttpServletRequest getRequest(){
		return req.get();
	}
	public static void setRequest(HttpServletRequest request){
		req.set(request);
	}
	public static void remove(){
		req.remove();
	}
}
