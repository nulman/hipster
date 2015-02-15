package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Post
 */
@WebServlet("/Post")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
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
		//POSTS(owner integer, mid, stamp TIMESTAMP ,replyto integer default null, 
				//republished int default 0,text varchar(140))
		//disect the request to get republish#, text, cookie.username
		int republish_id =Integer.parseInt(request.getParameter("republish").toString());
		Connection conn = null;
		String text=null;
		int uid = 0;
		Statement stmt = null;
		ResultSet results = null;
		//probably "fix" text by replacing %20 with spaces if that happens in post
		//check length of text<=140
		text=request.getParameter("text");
		if(text.length()>140){
			//if its too long craft an error response
			return;
		}else{
			//establish a database connection
			conn = Tools.getConnection();
			//get user #id (using the cookie username)
			Cookie[] cookies = request.getCookies();
		    if(cookies !=null){
			    for(Cookie cookie : cookies){
			    	//out.println(cookie.getName());
		        	if(cookie.getName().equals("hipsterUid")){
		        		uid=Integer.parseInt(cookie.getValue());
		        		break;
		        	}
		        }
		    }else{
		    	//error, no cookies! (it should be impossible to reach this state)
		    }
		  //check if this is a republish
		    if(republish_id>0){
		    	//if it is
				//query the original posts' republish amount and republish_to
		    	stmt = conn.createStatement();
				results = stmt.executeQuery("select REPUBLISH_OF, MID, TEXT from POSTS where "
						+ "MID='"+republish_id+"'");
				if(results.next()){
					uid=results.getInt("USER_ID");
					pic=results.getString("PIC");
		    }
		}
		//else 
		
		
		
		
		
		//(to check if we are republishing a republish) in a loop until republish_to is null
		//update republish amount and popularity
		//craft a Post entry with the appropriate fields
		//(no need to post the same thing twice, just link to the original text)
		//update users number of posts and hipster rank
		//close connection
		
		//else
		//check first word to see if its a @reply
		//if it is get the message #id we are replying to
		//check rest of the text to see any @mentions
		//check text to see any #subjects
		//add the text to the DB
		//query the text DB for the message you just posted unique #id
		//add post to the Posts table
		//add reply to the Replys table if needed
		//add mentions to the Mentions table if needed
		//add the subjects to the subjects table if needed
	}

}
