package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
         
        String uri = req.getRequestURI();
        System.err.println("*******in filter: "+uri.toString());
         
        //HttpSession session = req.getSession(false);
        Cookie[] cookies = req.getCookies();
	    if(cookies !=null){
		    for(Cookie cookie : cookies)
	        {
		    	//out.println(cookie.getName());
	        	if(cookie.getName().equals("hipsterUser")){
	        		System.err.println("++++++++found a cookie: "+cookie.getValue());
	        		chain.doFilter(request, response);
	        		return;
	        	}
	        }
	    }
	    System.err.println("------+no cookie: "+uri.toString());
	    if(uri.endsWith("login.html") || uri.endsWith("LoginSuccess.jsp") || uri.endsWith("LoginServlet")){
	    	System.err.println("------++:static page or login sevrlet: "+uri.toString());
	    	chain.doFilter(request, response);
	    	return;
	    }else{
	    	res.sendRedirect("/Hipster/login.html");
	    	return;
	    }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
    }

}
