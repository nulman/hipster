/**
 * 
 */
package internals;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.google.gson.stream.JsonWriter;

/**
 * @author Mr.Wootz
 *
 */
public class Tools {
	private static Context context = null;
	private static BasicDataSource ds = null;
	private static Connection conn = null;
	private static double log_base = Math.log(2.0);
	
	public static double Log2(double x){
		return(Math.log(x)/log_base);
	}
	public static Connection getConnection(){
		try {
			context = new InitialContext();
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
		}
		return(conn);
		
	}
	/**
	 * 
	 * @param response - the response we will be writing into
	 * @param results - the sql result set
	 */
	public static void ResSetToJSONRes(HttpServletResponse response, ResultSet results){
		try{
		ResultSetMetaData meta = results.getMetaData();
		response.setContentType("application/json; charset="+Constants.ENCONDING);
	    response.setCharacterEncoding(Constants.ENCONDING);
	    JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), Constants.ENCONDING));
	    writer.beginArray();
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
	    response.getOutputStream().flush();	
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
