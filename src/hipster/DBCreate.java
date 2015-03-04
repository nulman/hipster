package hipster;

import internals.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;


import org.apache.tomcat.dbcp.dbcp.BasicDataSource;




/**
 * Application Lifecycle Listener implementation class DBCreate
 *
 */
//@WebListener
public class DBCreate implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DBCreate() {
        // TODO Auto-generated constructor stub
    }
    
    //utility that checks whether the Hipster tables already exist
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    	//shut down database
   	/* try {
			DriverManager.getConnection("jdbc:derby:java:comp/env/jdbc/CustomerDatasource;shutdown=true");
		} catch (SQLException e) {
			ServletContext cntx = event.getServletContext();
			cntx.log("Error shutting down database",e);
		}*/
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	
    	try{
    		
    		//obtain DB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(Constants.DBSource);
    		Connection conn = ds.getConnection();
    		
    		boolean created = false;
    		try{
    			//create Hipster tables
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(Constants.mentions);
    			stmt.executeUpdate(Constants.posts);
    			stmt.executeUpdate(Constants.stalker);
    			stmt.executeUpdate(Constants.topic);
    			stmt.executeUpdate(Constants.users);
    			//commit update
        		conn.commit();
        		//stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since tables were already created (so we created the database already 
    			//in the past)
    			created = tableAlreadyExists(e);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		conn.close();

    	} catch (SQLException | NamingException e) {
    		//log error 
    		cntx.log("Error during database initialization",e);
    	}
    }
    		
	
}
