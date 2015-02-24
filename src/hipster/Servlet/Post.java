package hipster.Servlet;

import internals.Tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
		String [] returnGeneratedMID = new String [] { "MID" };
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
		if(temp!=null && temp.length()>0){
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
				owner = stmt.executeQuery("SELECT POPULARITY FROM USERS WHERE USER_ID="+uid);
				if(!owner.next()){
					//sql error
				}
				popularity = owner.getDouble("popularity");
	System.err.println("0");
				//check if this is a republish
				temp = request.getParameter("republish");
	System.err.println("0.4");
				if(temp!=null && temp.length()>0){
	System.err.println("0.5");
				republish_id=Integer.parseInt(temp);
				}
	//republish_id=1004;//delete this line!	
	System.err.println("0.6 did you delete the linea bove me?(manual alocation of republish_id)");
				if(republish_id>1){
	System.err.println("0.7");
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
	System.err.println("0.71");
					//get the message id of teh original publish
					republish_id=results.getInt("mid");
					owner = stmt2.executeQuery("select STALKERS from USERS where USER_ID="+results.getInt("OWNER"));
					if(!owner.next()){
						//sql error
					}
					//update the republish count and popularity of the post we are republishing
					stmt3.executeUpdate("UPDATE POSTS SET TIMES_REPUBLISHED="+(results.getInt("TIMES_REPUBLISHED")+1)
							+", POPULARITY="+(Tools.Log2(2+owner.getInt("STALKERS"))*Tools.Log2(2+(results.getInt("TIMES_REPUBLISHED")+1)))
							+" where MID="+results.getInt("MID"));
	System.err.println("0.72");
					//republish
					stmt2.executeUpdate("INSERT INTO POSTS(owner, republish_of, text, popularity) VALUES("+uid+", "
							+results.getInt("MID")+", '"+results.getString("TEXT")
							+"', "+Tools.Log2(2+popularity)+")",returnGeneratedMID);
					//get the new messege id
					results=stmt2.getGeneratedKeys();
	System.err.println("0.73");
					if(!results.next()){
						//failed to get auto generated keys, this shouldnt happen
						return;
					}
					mid=results.getInt(1);
	System.err.println("0.74");
					//get all the topics of the republished post
					topic=stmt3.executeQuery("select topic from topic where mid="+republish_id);
					//make the new post have all the topics from the original one
					while(topic.next()){
						stmt.executeUpdate("insert into topic(mid, topic) values("+mid+", '"
								+topic.getString("topic")+"')");
					}
	System.err.println("0.75");
					//add all the mentions from the original publish to this republish
					results = stmt.executeQuery("select mentionee from mentions where mentioner="+republish_id);
					while(results.next()){
					stmt2.executeUpdate("insert into mentions(mentioner,mentionee) values("+mid+",'"+results.getString("mentionee")+"')");
					}
	System.err.println("0.76");
					
					
		    }else{
		    	//we failed to retrieve the republished post, should only happen on a db error or by a smartass user
		    }
		}else{
			//normal publish or reply
			System.err.println("1");
			if(text.length()==0){
				//we dont allow empty posts
				return;
			}
			//cuts out all the words in the text that start with @
			String [] prework = text.split(" ");
			String mentions = null;
			for(String mention : prework){
				if(mention.startsWith("@")){
					results = stmt.executeQuery("select user_id from users where nickname='"+mention.substring(1)+"'");
					if(!results.next()){
						continue;
					}
					//replace legal @mentions with link to profile pages of relevant users
					text=text.replace(mention, "<a href=\"/Hipster/users/"+mention.substring(1)+"\">"+mention+"</a>");
					//constructs a string of mentions "@mention,@mention2..."
					if(mentions == null){
						mentions= new StringBuilder().append(mention).toString();
						System.err.println("mentions was empty and is now: "+mentions);
					}else{
						mentions= new StringBuilder().append(mentions).append(",").append(mention).toString();
						System.err.println("mentions was NOT empty and is now: "+mentions);
					}
				}
			}
			//get all the subject
			for(String subject : prework){
				if(subject.startsWith("#")){
					//replace all #topics with links to the topic
					text=text.replace(subject, "<a href=\"/Hipster/topic/"+subject.substring(1)+"\">"+subject+"</a>");
					//add relevant topics to the subjects table
					stmt.executeUpdate("insert into topic(mid,topic) values("+mid+",'"+subject+"')");
					System.err.print(subject+" ");
				}
			}
			System.err.println("about to post: "+text);
			stmt.executeUpdate("INSERT INTO POSTS(owner, text, popularity, replyto) VALUES("+uid
					+", '"+text+"', "+Tools.Log2(2+popularity)+", "+reply_to+")", returnGeneratedMID);
			System.err.println("2");
			//get the new messege id
			results=stmt.getGeneratedKeys();
			//if(!results.next()){
				//failed to get auto generated keys, this shouldnt happen
			//}
			System.err.println("2.5");
		       if (!results.next()) {
		    		//failed to get mid
		    		return;
		    	}
		       mid = results.getInt(1);
			System.err.println(mid+"\n3");
			
			//adds relevant information to the mentions(id of the person that is mentioned, id of the mentioning post) table
			if (mentions!=null){
			stmt.executeUpdate("insert into mentions(mentionee, mentioner) values('"+mentions+"' ,"+mid+")");
			System.err.print("inserted into mentions: "+mentions);
			}
			System.err.println();
			System.err.println("4");
				
			System.err.println();
			System.err.println("5");
		}
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
