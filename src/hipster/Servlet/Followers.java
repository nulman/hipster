package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Followers
 */
@SuppressWarnings("deprecation")
//@WebServlet({"/followers", "/followers/*"})
public class Followers extends HttpServlet implements SingleThreadModel{
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
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		String nickname = null;
		String [] temp = request.getRequestURI().split("/");	
		nickname=temp[temp.length-1];
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			System.err.println("nickname="+nickname);
			results = stmt.executeQuery("select user_id from users where nickname='"
					+nickname+"'");
			System.err.println("query successfull");
			if(results.next()){
				response.sendRedirect("/Hipster/followers.html?nickname="+nickname);
			}else{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
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
			
			results = stmt.executeQuery("select stalker.stalker, users.nickname, users.pic from stalker join users on "
					+"stalker.stalker=users.nickname where stalker.stalkee='"
			+Tools.RequestToString(request)+"' order by users.popularity desc fetch first 10 rows only");
			Tools.ResSetToJSONRes(response, results);
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
