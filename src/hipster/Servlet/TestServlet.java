package hipster.Servlet;

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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.derby.jdbc.ClientDriver;

//import org.apache.derby.iapi.sql.ResultSet;
//import org.apache.derby.iapi.sql.conn.SQLSessionContext;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.util.http.Cookies;

import com.google.gson.stream.JsonWriter;



/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet/*")
public class TestServlet extends HttpServlet {
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
		//response.setContentType("text/html");
	    //PrintWriter out = response.getWriter();
	    //out.println("you are trying to access <font color=red>" + get[get.length-1] + "</font>\n");
	    //Context context = null;
	    //BasicDataSource ds = null;
	    Connection conn = Tools.getConnection();
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
	    ResultSet results = stmt.executeQuery("select * from TEXT");
	    ResultSetMetaData rsmd = results.getMetaData();
	    int numberCols = rsmd.getColumnCount();
	    Cookie[] cookies = request.getCookies();
	    if(cookies !=null){
		    for(Cookie cookie : cookies)
	        {
		    	//out.println(cookie.getName());
	        	//if(cookie.getName().equals("hipsterUser"))
	        	//	out.println("cookie name="+cookie.getName()+" cookie value="+cookie.getValue());
	        }
	    }
	    //gson: results set to json method
	    Tools.ResSetToJSONRes(response, results);
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