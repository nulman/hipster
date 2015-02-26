package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Discover
 */
@WebServlet("/Discover")
public class Discover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Discover() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated stub
		request.setAttribute("author", "test");
		request.setAttribute("sort_by", "time");
		System.err.println("in discover GET. got author:sort_by "+(String)request.getAttribute("author")
				+":"+(String)request.getAttribute("sort_by"));
		RequestDispatcher rd = request.getRequestDispatcher("/LoginSuccess.jsp");
        
        
		doPost(request,response);
		rd.include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet results = null;
		HttpSession session = request.getSession();
		String author = null;
		String sort_by = null;
		
		//check what we need to return
		author = (String)request.getAttribute("author");
		sort_by = (String)request.getAttribute("sort_by");
	System.err.println("in discover. got author:sort_by "+author+":"+sort_by);
		try{
			conn = Tools.getConnection();
			stmt = conn.createStatement();
			//what is are teh posts sorted by?
			if(sort_by.equals("popularity")){
				sort_by = "popularity";
			}else{
				sort_by = "stamp";
			}
			//posts by whom?
			if(author.equals("all")){
				author = "<>"+Integer.parseInt(session.getAttribute("user_id").toString());
			}else if(author.equals("stalkee")){
					author = "=(select stalkee_id from stalker where stalker='"
				+session.getAttribute("nickname").toString()+"')";
			}else{
				author="=(select user_id from users where nickname='"+author+"')";
			}
			results = stmt.executeQuery("select posts.*, users.nickname, users.pic from posts join users "
					+"on users.user_id = posts.owner where users.user_id"+author
					+" order by posts."+sort_by+" desc fetch first 10 rows only");
			//debug code
			/*ResultSetMetaData meta = results.getMetaData();
			while(results.next()){
				 for(int i=1,col=meta.getColumnCount(); i<=col; i++) {
			         System.err.print(meta.getColumnLabel(i)+" : ");
			         System.err.println(results.getString(i));
			       }
			}*/
			Tools.ResSetToJSONRes(response, results);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
