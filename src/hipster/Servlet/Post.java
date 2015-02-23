package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		int republish_id = 0;
		Connection conn = null;
		String text=null;
		int reply_to = 0;
		String temp= null;
		int uid = 0;
		int mid = 0;
		double popularity = 0.0;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet results = null;
		ResultSet owner =null;
		ResultSet topic = null;
		
		//check if we got a reply_to
		temp = request.getParameter("reply_to");
		if(temp.length()>0){
			reply_to=Integer.parseInt(temp);
		}
		//probably "fix" text by replacing %20 with spaces if that happens in post
		//check length of text<=140
		text=request.getParameter("text");
		if(text.length()>140){
			//if its too long craft an error response
			return;
		}
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
		    	//error, no cookies! (it should be impossible to reach this state unless the filter is off)
		    }
		    try{
		    	// we will need at least one sql statement
		    	stmt = conn.createStatement();
		    	//initial popularity of posts is based on the amount of followers the user has
				owner = stmt.executeQuery("SELECT STALKERS,NAME FROM USERS WHERE USER_ID="+uid);
				if(!owner.next()){
					//sql error
				}
				popularity = owner.getDouble("popularity");
		  //check if this is a republish
		    republish_id =Integer.parseInt(request.getParameter("republish"));
		    if(republish_id>0){
		    	//if it is
				//query the original posts' republish amount and republish_of
		    	stmt2 = conn.createStatement();
		    	stmt3 = conn.createStatement();
				results = stmt.executeQuery("select * from POSTS where "
						+ "MID="+republish_id);
				if(results.next()){
					//if result doesnt have the parent post, get the parent
					if(results.getInt("REPUBLISH_OF")>0){
						results = stmt.executeQuery("select * from POSTS where "
								+ "MID="+results.getInt("REPUBLISH_OF"));
						if(!results.next()){
							//error retrieving the original post. this shouldnt happen.
						}
					}
					//get the message id of teh original publish
					republish_id=results.getInt("mid");
					owner = stmt2.executeQuery("select STALKERS from USESRS where USER_ID="+results.getInt("OWNER"));
					if(!owner.next()){
						//sql error
					}
					//update the republish count and popularity of the post we are republishing
					stmt.executeUpdate("UPDATE POSTS SET TIMES_REPUBLISHED="+(results.getInt("TIMES_REPUBLISHED")+1)
							+", POPULARITY="+(Tools.Log2(2+owner.getInt("STALKERS"))*Tools.Log2(2+(results.getInt("TIMES_REPUBLISHED")+1)))
							+" where MID="+results.getInt("MID"));
					//republish
					stmt.executeUpdate("INSERT INTO POSTS(owner, republish_of, text, popularity) VALUES("+uid+", "
							+results.getInt("MID")+", '"+results.getString("TEXT")
							+"', "+Tools.Log2(2+popularity)+")");
					//get the new messege id
					results=stmt.getGeneratedKeys();
					if(!results.next()){
						//failed to get auto generated keys, this shouldnt happen
					}
					mid=results.getInt("mid");
					//get all the topics of the republished post
					topic=stmt3.executeQuery("select topic from topic where mid="+republish_id);
					//make the new post have all the topics from the original one
					while(topic.next()){
						stmt.executeUpdate("insert into topic(mid, topic) values("+mid+", '"
								+topic.getString("topic")+"')");
					}
					//add all the mentions from the original publish to this republish
					results = stmt.executeQuery("select mentioner from mentions where mentioner="+republish_id);
					while(results.next()){
					stmt2.execute("insert into mentions(mentionee,mentioner) values("+mid+","+results.getInt("mentioner")+")");
					}
					
					
		    }else{
		    	//we failed to retrieve the republished post, should only happen on a db error or by a smartass user
		    }
		}else{
			//normal publish or reply
			if(text.length()==0){
				//we dont allow empty posts
				return;
			}
			System.err.println("about to post: "+text);
			stmt.executeUpdate("INSERT INTO POSTS(owner, text, popularity, replyto) VALUES("+uid+", "
					+", '"+text+"," + ", "+Tools.Log2(2+popularity)+reply_to+")");
			//get the new messege id
			results=stmt.getGeneratedKeys();
			if(!results.next()){
				//failed to get auto generated keys, this shouldnt happen
			}
			mid = results.getInt("mid");	
			//cuts out all the words in the text that start with @
			String [] prework = text.split(" ");
			for(String mention : prework){
				if(mention.startsWith("@")){
					//adds relevant information to the mentions(id of the person that is mentioned, id of the mentioning post) table	
					stmt.executeUpdate("insert into mentions(mentionee, mentioner) values((select user_id from users where nickname='"
							+mention.substring(1)+"') as temp,"+mid+")");
				}
			}
			//get all the subject
			for(String subject : prework){
				if(subject.startsWith("#")){
					//add relevant topics to the subjects table
					stmt.executeUpdate("insert into topic(mid,topic) values("+mid+",'"+subject+"')");
				}
			}	
		}
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
