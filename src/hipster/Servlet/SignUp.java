package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@SuppressWarnings("deprecation")
//@WebServlet("/SignupServlet")
public class SignUp extends HttpServlet implements SingleThreadModel{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		String username = null;
		String nickname = null;
		String password = null;
		String pic = null;
		String description = null;
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet results = null;
		ResultSet results2 = null;
		RequestDispatcher rd = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		username=request.getParameter("username");
		nickname=request.getParameter("nickname");
		password=request.getParameter("password");
		pic=request.getParameter("imgurl");
		description=request.getParameter("description");
		
		try{
			conn = Tools.getConnection();
			stmt=conn.createStatement();
			stmt2=conn.createStatement();
			results=stmt.executeQuery("select user_id from users where username='"+username+"' fetch first row only");
			results2=stmt2.executeQuery("select user_id from users where username='"+nickname+"' fetch first row only");
			if(pic==null || pic.length()<1){
				pic= "resources/images/defaultuserimg.png";
			}
			//make sure the user isnt trying to sign up with reserved names
			if(internals.Constants.Illegal_Names.contains(username)){
				out.println("<div><font size=20>the name "+username+" is already taken</font></div>");
			}else if(internals.Constants.Illegal_Names.contains(username)){
				out.println("<div><font size=20>the name "+nickname+" is already taken</font></div>");
			}
			//username already exists
			else if(results.next()){
				out.println("<div><font size=20>the name "+username+" is already taken</font></div>");
			}
			//username already exists
			else if(results2.next()){
				out.println("<div><font size=20>the name "+nickname+" is already taken</font></div>");
			}else{
				//this is indeed a new user
				System.err.println("insert into users(username, password, nickname, pic, description) values('"+username+"', '"
						+password+"', '"+nickname+"', '"+pic+"', '"+description+"')");
					stmt.execute("insert into users(username, password, nickname, pic, description) values('"+username+"', '"
						+password+"', '"+nickname+"', '"+pic+"', '"+description+"')");
					out.println("<div><font size=20>registration successful! please log in to continue</font></div>");

			}
			stmt.close();
			stmt2.close();
			conn.close();
			rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response);
            //response.sendRedirect("/Hipster/login.html?msg=\"something something\"");
            out.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		//extract username from request
		//establish a db connection
		//query if the username already exists
		//if it does close connection and report it
		
		//if it doesnt exist
		//extract the rest of the data from the request
		//create new entry in the users table
		//report success and redirect to login screen
		//close db connection
	}

}
