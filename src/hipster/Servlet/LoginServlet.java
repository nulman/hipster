package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@SuppressWarnings("deprecation")
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet implements SingleThreadModel{
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
		HttpSession session = null;
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
					//debug code that prints all the
					/*System.err.print("loginservlet. request. name="+name+" pass="+pass+" parameters=");
					Enumeration<String> en=request.getParameterNames();
					 
					while(en.hasMoreElements())
					{
					Object objOri=en.nextElement();
					String param=(String)objOri;
					String value=request.getParameter(param);
					System.err.println(param+" "+value);
					}*/
		System.err.println("about to query");
			results = stmt.executeQuery("select USER_ID, PIC, USERNAME, NICKNAME from USERS where "
					+ "USERNAME='"+name+"'AND PASSWORD='"+pass+"'");
		System.err.println("got this far");
			if(results.next()){
				uid=results.getInt("USER_ID");
				pic=results.getString("PIC");
	//out.println("<font color=red size=20>username="+name+" pass="+pass+" id="+uid+" pic_url="+pic+" </font>\n");
	System.err.println("<font color=red>username="+name+" pass="+pass+" id="+uid+" pic_url="+pic+" </font>\n");
				session = request.getSession();
				session.setAttribute("username", results.getString("username"));
				session.setAttribute("user_id", results.getInt("user_id"));
				session.setAttribute("nickname", results.getString("nickname"));
				session.setAttribute("photo", results.getString("pic"));
				
				session.setMaxInactiveInterval(60*30);

	            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/mainPage.html");
	            //rd.include(request, response);
				response.sendRedirect("/Hipster"); 
	            out.close();
	          //close DB connection
	            stmt.close();
				conn.close();
	            return;
			}else{
				//close DB connection
				stmt.close();
				conn.close();
	            //PrintWriter out= response.getWriter();
	            out.println("<div class="+"'response'>"+"Wrong username/password.</div>");
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
	            rd.include(request, response);
	            //response.sendRedirect("/Hipster/login.html");
	            out.close();
	            return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
