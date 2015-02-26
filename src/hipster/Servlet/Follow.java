package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Follow
 */
@WebServlet(urlPatterns = { "/Follow" })
public class Follow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Follow() {
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
		// TODO Auto-generated method stub
		
		
		Connection conn = null;
		int user_id = 0;
		double popularity = 0.0;
		String temp = null;
		user_id = 0;
		Statement stmt= null;
		ResultSet  results = null;
		
		temp = request.getParameter("user_id");
		if(temp!=null && temp.length()>0){
			user_id=Integer.parseInt(temp);
		}
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			//check for duplicate
			results = stmt.executeQuery("select * from stalker where stalker="+user_id+" stalkee="+current_user+")");
			if(results.next()){
				//this is a duplicate, handle it
				return;
			}
			//get the profile
			results = stmt.executeQuery("select stalkers from users where user_id="+user_id);
			if(results.next()){
				popularity = Tools.Log2(2.0+(results.getInt("stalkers")+1));
				//increment the stalkers and update the user popularity
				stmt.executeUpdate("update users set stalkers="+(results.getInt("stalkers")+1)
						+ " popularity="+popularity
						+"where user_id="+user_id);
			}
			//make the relationship to the stalker table
			stmt.executeUpdate("insert into stalker(stalker,stalkee) values("+current_user+", "+user_id+")");
			//get all the users posts
			results = stmt.executeQuery("select mid,times_republished from posts where owner="+user_id);
			while(results.next()){
				//for each post by the user, update popularity
				stmt.executeUpdate("update posts set popularity="+(Tools.Log2(2.0+results.getInt("times_republished"))*popularity)
						+"where mid="+results.getInt("mid"));
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
