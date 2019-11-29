
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class DatabaseConnector {

	/**
	 * @param args
	 */
	
	private static Connection con=null;
	private ResultSet rs=null;
	private PreparedStatement pstmt= null;
	ISBNDAO dao = new ISBNDAO();
	String env = "dev";
	//private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class);
	
	

	
	public DatabaseConnector()
	{
		Properties prop = new Properties();
		try {
			
			prop.load(getClass().getClassLoader().getResourceAsStream("Database.properties"));
			String url = prop.getProperty(env + ".url");
			String user = prop.getProperty(env + ".user");
			String password = prop.getProperty(env + ".password");
			
			//System.out.println("Properties values are :-  " +url + "User :- " +user + "Password :- "+password);
			
			con = DriverManager.getConnection(url,user,password);
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Can't connect ORACLE from R");
			e.printStackTrace();
		}
		
	}
	
	 public boolean check_conn() {
		 
	     boolean conn_flag=true;
	     try {
	    	 if(con.isClosed()) {
	    		 conn_flag=false;
	    		 }
	       	}catch (SQLException e) {
	       		System.out.println("Database connection error");
	       		conn_flag=false; 
	       		return  conn_flag;
	       		}
	     return  conn_flag;
         }
	
	 public void closeConnection() 
	 {
	 	try {
	 		con.close();
	 	} catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	 }
	
	public String selectIsbn10forIsbn13(String isbn13){

		String isbn10 = null;
		try {
			String sql="select i400.isbn from isbn_as400 i400 where i400.isbn13 = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn13);
			
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				isbn10=rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isbn10;
	}
	
	public String selectIsbn10(String isbn10){

		String isbn = null;
		try {
			String sql="select i400.isbn from isbn_as400 i400 where i400.isbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn10);
			
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				isbn = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isbn;
	}
	
	
	public String getrelatedISBN(String isbn10){
		
		String relatedISBN="";
		relatedISBN = dao.getrelatedISBN(con,isbn10);
		return relatedISBN;
	}

	public String[] getTitleSubTitle(String isbn10) {
		String [] titleDetails = new String[2];
		titleDetails = dao.getTitleSubTitle(con,isbn10);
		return titleDetails;
	}

	public ISBNVO getisbn10andisbn13(String isbn10) {
		ISBNVO isbnvo = new ISBNVO();
		isbnvo = dao.getisbn10andisbn13(con,isbn10);
		return isbnvo;
	}
	
	/*public String selectIsbn10forIsbn13(Connection con,String isbn10){
	return dao.selectIsbn10forIsbn13(con,isbn10);
	}*/
	
}
