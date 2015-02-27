package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Followers
 */
@WebServlet("/followers")
public class Followers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Followers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// extract name
		//establish a db connection
		//query for name and his #id
		//query list of top 10 followers by popularity
		//close connection
		//craft response
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			
			results = stmt.executeQuery("select stalker.stalker from stalker join users on "
					+"stalker.stalker=users.nickname where stalker.stalkee='"
			+request.getAttribute("nickname").toString()+"' order by users.popularity desc fetch first 10 rows only");
			Tools.ResSetToJSONRes(response, results);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
