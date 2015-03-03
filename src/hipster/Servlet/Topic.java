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
 * Servlet implementation class Topic
 */
@SuppressWarnings("deprecation")
//@WebServlet({"/topic/*", "/topic"})
public class Topic extends HttpServlet implements SingleThreadModel{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Topic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] uri = request.getRequestURI().split("/");
		String topic = uri[uri.length-1];
		while(topic.startsWith("#")){
			topic = topic.substring(1);
		}
		response.sendRedirect("/Hipster/topic.html?topic="+topic);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		String topic = Tools.RequestToString(request);
		
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			System.err.println("in topic, got tpoic: "+topic);
			//results = stmt.executeQuery("select posts.* from posts join topic on "
			//		+"posts.mid=topic.mid where topic.topic='"
			//+topic+"' order by posts.stamp desc fetch first 10 rows only");
			results = stmt.executeQuery("select posts.*, users.nickname, users.pic from posts "
					+"join users on users.user_id = posts.owner where posts.mid in(select mid from topic where topic.topic ='"+
					topic+"') order by posts.stamp desc fetch first 10 rows only");
			Tools.ResSetToJSONRes(response, results);
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
