package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Follow
 */
@SuppressWarnings("deprecation")
@WebServlet(urlPatterns = { "/Follow" })
public class Follow extends HttpServlet implements SingleThreadModel{
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
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		Connection conn = null;
		double popularity = 0.0;
		String stalkee = null;
		Statement stmt= null;
		Statement stmt2= null;
		ResultSet  results = null;
		HttpSession session = request.getSession(false);
		String stalker = session.getAttribute("nickname").toString();
		int stalkee_id = 0;
		int curr_user_id = Integer.parseInt(session.getAttribute("user_id").toString());
		
		stalkee = request.getParameter("nickname");
		if(stalkee==null){
			//bad request!
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			//check for duplicate
			results = stmt.executeQuery("select * from stalker where stalker='"+stalker+"' and stalkee='"+stalkee+"'");
			if(results.next()){
				//this is a duplicate, handle it
				stmt.close();
				conn.close();
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
			results = stmt.executeQuery("select user_id from users where nickname='"+stalkee+"'");
			if(!results.next()){
				//no such user
				stmt.close();
				conn.close();
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
			//increment the amount of people the user is following
			stalkee_id=results.getInt("user_id");
			results = stmt.executeQuery("select stalkees from users where user_id="+curr_user_id);
			if(results.next()){
				stmt2.executeUpdate("update users set stalkees="+(results.getInt("stalkees")+1) +"where user_id="
			+curr_user_id);
			}
			//get the profile
			results = stmt.executeQuery("select stalkers from users where user_id="+stalkee_id);
			if(results.next()){
				popularity = Tools.Log2(2.0+(results.getInt("stalkers")+1));
				//increment the stalkers and update the user popularity
				stmt.executeUpdate("update users set stalkers="+(results.getInt("stalkers")+1)
						+ ", popularity="+popularity
						+"where user_id="+stalkee_id);
			}
			//make the relationship in the stalker table
			stmt.executeUpdate("insert into stalker(stalker,stalkee,stalkee_id) values('"+stalker+"', '"+stalkee+"',"
					+stalkee_id+")");
			//get all the users posts
			results = stmt.executeQuery("select mid,times_republished from posts where owner="+stalkee_id);
			while(results.next()){
				//for each post by the user, update popularity
				stmt2.executeUpdate("update posts set popularity="+(Tools.Log2(2.0+results.getInt("times_republished"))*popularity)
						+"where mid="+results.getInt("mid"));
			}
			response.sendError(HttpServletResponse.SC_NO_CONTENT);
			stmt.close();
			conn.close();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
