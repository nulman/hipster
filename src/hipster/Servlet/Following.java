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

/**
 * Servlet implementation class Following
 */
@WebServlet("/following/*")
public class Following extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Following() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//extract name
		//establish a db connection
		//query to check if name exists and to gets its #id
		//query 10 posts from people name follows ordered by popularity
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
			
			results = stmt.executeQuery("select stalker.stalkee from stalker join users on "
					+"stalker.stalkee=users.nickname where stalker.stalker='"
			+request.getAttribute("nickname").toString()+"' order by users.popularity desc fetch first 10 rows only");
			Tools.ResSetToJSONRes(response, results);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
