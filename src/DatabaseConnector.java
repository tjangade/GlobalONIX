
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
	String env = "dev";
	ISBNDAO dao = new ISBNDAO();
	workDAO workdao = new workDAO();
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
	
	public ISBNVO getseriesSeq(String isbn10) {
		ISBNVO isbnvo = new ISBNVO();
		isbnvo = dao.getseriesSeq(con,isbn10);
		return isbnvo;
	}
	
	public workVO getparentID(String isbn10) {
		workVO workvo = new workVO();
		workvo = workdao.getparentID(con,isbn10);
		return workvo;
	}

}
