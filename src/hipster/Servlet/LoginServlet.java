package hipster.Servlet;

import internals.Constants;
import internals.Tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.StandardServer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = null;
		String pass = null;
		String pic = null;
		int uid =0;
		Statement stmt = null;
		ResultSet results = null;
		name = request.getParameter("username");
		pass = request.getParameter("password");
		PrintWriter out = response.getWriter();
	//out.println("name="+name+" pass="+pass);
		try {
			//connect to derby
			Connection conn = Tools.getConnection();
			stmt = conn.createStatement();
			
					System.err.print("loginservlet. request. name="+name+" pass="+pass+" parameters=");
					Enumeration<String> en=request.getParameterNames();
					 
					while(en.hasMoreElements())
					{
					Object objOri=en.nextElement();
					String param=(String)objOri;
					String value=request.getParameter(param);
					System.err.println(param+" "+value);
					}

			results = stmt.executeQuery("select USER_ID, PIC from USERS where "
					+ "USERNAME='"+name+"'AND PASSWORD='"+pass+"'");
			if(results.next()){
				uid=results.getInt("USER_ID");
				pic=results.getString("PIC");
				//close DB connection
				conn.close();
	//this doesnt get sent...
	out.println("<font color=red>username="+name+" pass="+pass+" id="+uid+" </font>\n");
	System.err.println("<font color=red>username="+name+" pass="+pass+" id="+uid+" </font>\n");
				Cookie cookie = new Cookie("hipsterUser", name);
	            // setting cookie to expiry in 60 mins
				cookie.setMaxAge(60 * 60);
	            response.addCookie(cookie);
	            System.err.println("///in loging servlet, added cookie: "+ cookie.getName());
	            /*cookie = new Cookie("hipsterUid", String.valueOf(uid));
	            // setting cookie to expiry in 60 mins
				cookie.setMaxAge(60 * 60);
	            response.addCookie(cookie);
	            cookie = new Cookie("hipsterPic", pic);
	            // setting cookie to expiry in 60 mins
				cookie.setMaxAge(60 * 60);
	            response.addCookie(cookie)*/;
	            //redirect to personal page
	            //response.sendRedirect("/Hipster/LoginSuccess.jsp");
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/LoginSuccess.jsp");
	            rd.include(request, response);
	            out.close();
	            return;
			}else{
				//close DB connection
				conn.close();
	            //PrintWriter out= response.getWriter();
	            out.println("<font color=red>Your creds aren\'t legit enough for Hipster.</font>");
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
	            rd.include(request, response);
	            //response.sendRedirect("/Hipster/login.html");
	            out.close();
	            return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
