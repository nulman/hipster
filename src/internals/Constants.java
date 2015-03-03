package internals;

public interface Constants {
	public final String ENCONDING = "UTF-8";
	public final String Illegal_Names = "stalker,stalkee,posts,all,user";
	public final String DBSource ="java:comp/env/jdbc/hipster_DB";
	
	public final String users = "CREATE TABLE USERS(user_id INTEGER GENERATED ALWAYS AS IDENTITY "
			+"(START WITH 1, INCREMENT BY 1), popularity DOUBLE default 1.0, password varchar(20), pic varchar(150)"
			+", nickname varchar(20) unique, username varchar(20) unique, stalkers integer default 0, description varchar(300),"
			+" stalkees integer default 0)";
	public final String topic = "CREATE TABLE TOPIC(topic varchar(50), mid integer)";
	public final String posts = "CREATE TABLE POSTS(owner integer, mid INTEGER GENERATED ALWAYS AS IDENTITY"
			+"(START WITH 1, INCREMENT BY 1), stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
			+"replyto integer default 0, times_republished int default 0,popularity  double default 0.0,"
			+"republish_of int default 0,text varchar(500))";
	public final String stalker = "CREATE TABLE STALKER(stalker varchar(20) NOT NULL, stalkee varchar(20) NOT NULL,"
			+"stalkee_id int)";
	public final String mentions = "CREATE TABLE MENTIONS(mentioner INTEGER, mentionee varchar(140))";
}
