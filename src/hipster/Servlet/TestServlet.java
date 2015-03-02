package hipster.Servlet;

import internals.Constants;
import internals.Tools;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.derby.jdbc.ClientDriver;

//import org.apache.derby.iapi.sql.ResultSet;
//import org.apache.derby.iapi.sql.conn.SQLSessionContext;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.util.http.Cookies;

import com.google.gson.stream.JsonWriter;



/**
 * Servlet implementation class TestServlet
 */
@SuppressWarnings("deprecation")
@WebServlet("/TestServlet/*")
public class TestServlet extends HttpServlet implements SingleThreadModel{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String get[] = request.getRequestURI().split("/");
		int uid =0;
		//response.setContentType("text/html");
	    //PrintWriter out = response.getWriter();
	    //out.println("you are trying to access <font color=red>" + get[get.length-1] + "</font>\n");
	    //Context context = null;
	    //BasicDataSource ds = null;
	    Connection conn = Tools.getConnection();
	    HttpSession session = request.getSession();
	    request.setAttribute("author", "all");
	    request.setAttribute("sort_by", "time");
	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Discover");
        rd.include(request, response);
        if(rd != null){
        	return;
        }
		/*try {
			context = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ds = (BasicDataSource)context.lookup("java:comp/env/jdbc/hipster_DB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	    String dbURL = "jdbc:derby:C:/Users/Mr.Wootz/workspace/Hipster/DB;user=user;password=password";
	    //String dbURL="jdbc:derby:DB;user=user;password=password";
	    /*Connection conn = null;
	    try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }*/
	    try{
	    Statement stmt = conn.createStatement();
	    Statement statement = conn.createStatement();
	    ResultSet results = stmt.executeQuery("select * from TEXT");
	    ResultSet other = null;
	    ResultSetMetaData rsmd = results.getMetaData();
	    int numberCols = rsmd.getColumnCount();
	    //Cookie[] cookies = request.getCookies();
	    /*if(cookies !=null){
		    for(Cookie cookie : cookies)
	        {
		    	//out.println(cookie.getName());
	        	//if(cookie.getName().equals("hipsterUser"))
	        	//	out.println("cookie name="+cookie.getName()+" cookie value="+cookie.getValue());
	        }
	    }*/
	    //gson: results set to json method
	    //Tools.ResSetToJSONRes(response, results);
	    //System.err.println("about to sql\n");
	    /*stmt.execute("INSERT INTO POSTS(owner, text, popularity) VALUES("+7+", "
				+"'this is another test  <a href=http://youtu.be/dQw4w9WgXcQ>@hrefs</a>  i dont know what an href looks like'"
				+", "+42+")");*/
	    //System.err.println("post sql\n");
	    /*System.err.println(request.getContextPath());
	    response.sendRedirect("/Hipster/LoginSuccess.jsp");
	    results = stmt.executeQuery("select * from posts inner join mentions on mentions.mentioner=posts.mid where posts.mid=502");
	    ResultSetMetaData meta = results.getMetaData();
	    while(results.next()) {
	       // generates name:value json pairs
	       for(int i=1,col=meta.getColumnCount(); i<=col; i++) {
	         System.err.print(meta.getColumnLabel(i)+" : ");
	         System.err.println(results.getString(i));
	       }
	    }*/
	    //Tools.ResSetToJSONRes(response, results);
	    //other = statement.executeQuery("select description,stalkers,popularity,username,nickname, pic, user_id from users where user_id=4");
	    //Tools.ResSetToJSONRes(response, results);
				/*int n =1;
				ResultSetMetaData meta = null;
				response.setContentType("application/json; charset="+Constants.ENCONDING);
			    response.setCharacterEncoding(Constants.ENCONDING);
			    JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), Constants.ENCONDING));
			    writer.beginArray();
			    meta = other.getMetaData();
			    while(other.next()) {
			       writer.beginObject();
			       // generates name:value json pairs
			       for(int i=1,col=meta.getColumnCount(); i<=col; i++) {
			         writer.name(meta.getColumnLabel(i));
			         writer.value(other.getString(i));
			       }
			       writer.endObject();
			    }
			    writer.beginObject();
			    writer.name("reply");
			    writer.value("");
			    writer.endObject();
			    writer.beginObject();
			    writer.name("mention");
			    writer.value("@exists");
			    writer.endObject();
			    meta = results.getMetaData();
			    while(results.next()) {
			       writer.beginObject();
			       // generates name:value json pairs
			       for(int i=1,col=meta.getColumnCount(); i<=col; i++) {
			         writer.name(meta.getColumnLabel(i));
			         writer.value(results.getString(i));
			       }
			       writer.endObject();
			    }
			    
			    writer.endArray();
			    writer.close();
			    response.getOutputStream().flush();*/
				
	    
	    //out.close();
	    /*response.setContentType("application/json; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
	    writer.beginArray();
	    while(results.next()) {
	       writer.beginObject();
	       // loop rs.getResultSetMetadata columns
	       for(int idx=1; idx<=rsmd.getColumnCount(); idx++) {
	         writer.name(rsmd.getColumnLabel(idx)); // write key:value pairs
	         writer.value(results.getString(idx));
	       }
	       writer.endObject();
	    }
	    writer.endArray();
	    writer.close();
	    response.getOutputStream().flush();*/
	    //sending direct text method
	    /*out.println("\n----------------------------------\n");
        for (int i=1; i<=numberCols; i++)
        {
            //print Column Names
            out.print(rsmd.getColumnLabel(i)+"\t\t");  
        }
        out.println("\n----------------------------------\n");
        while(results.next()){
        	for (int i=1; i<=numberCols; i++)
        		out.print(results.getString(i) + "\t\t");
        	out.println("\n");
	    }*/
        	conn.close();
	    }catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
