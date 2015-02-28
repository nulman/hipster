package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserPage
 */
@WebServlet({"/user/*", "/user"})
public class UserPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		String nickname = null;
		String [] temp = request.getRequestURI().split("/");	
		nickname=temp[temp.length-1];
		RequestDispatcher rd = null;
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			System.err.println("nickname="+nickname);
			results = stmt.executeQuery("select user_id from users where nickname='"
					+nickname+"'");
			System.err.println("query successfull");
			if(results.next()){
				rd = getServletContext().getRequestDispatcher("/profile.html?nickname=\""+nickname+"\"");
	            //rd.include(request, response);
				response.sendRedirect("/Hipster/profile.html?nickname=\""+nickname+"\"");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			System.err.println("user page doPost nickname="+request.getAttribute("nickname"));
			results = stmt.executeQuery("select nickname,pic,user_id,description,stalkers,stalkees from users where nickname="
					+request.getAttribute("nickname"));
			Tools.ResSetToJSONRes(response, results);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
