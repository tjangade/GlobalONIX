
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ISBNDAO {
	
	private ResultSet rs=null;
	private PreparedStatement pstmt= null;
	
	
	public String[] getTitleSubTitle(Connection con,String isbn10)
	{
		String[] titleDetails = new String[2];
		titleDetails[0] = "";
		titleDetails[1] = "";
		
	try {
		String sql="select TITLE,SUBTITLE from WORK w,ISBN i where w.WORK_ID = i.WORK_ID and i.ISBN = ?";
		pstmt= con.prepareStatement(sql);
		pstmt.setString(1, isbn10);
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			titleDetails[0] = "" + rs.getString(1);
			titleDetails[1] = "" + rs.getString(2);
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
	return titleDetails;
	}


	public ISBNVO getisbn10andisbn13(Connection con,String isbn10) {
		
		ISBNVO isbnvo = new ISBNVO();
		try {
			String sql = "select ISBN,ISBN13,COUNTRY_OF_ORIGIN,STATUS_CODE from ISBN_AS400 where ISBN = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn10);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				isbnvo.setIsbn(rs.getString(1));
				isbnvo.setIsbn13(rs.getString(2));
				isbnvo.setCounntryCode(rs.getString(3));
				isbnvo.setStatusCode(rs.getString(4));
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
		return isbnvo;
	}


	public ISBNVO getseriesSeq(Connection con, String isbn10) {
		ISBNVO isbnvo = new ISBNVO();
		String serieSeq	 = "";
		String seriesCode = "";
		try {
			String sql = "select SERIES_SEQ,SERIES_CODE from ISBN_AS400 where ISBN = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn10);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				serieSeq = "" +  rs.getString(1);
				seriesCode = "" +  rs.getString(2);
				
				if(!serieSeq.equals("null") && !seriesCode.equals("null")){
					isbnvo.setSeriesSeq(rs.getString(1));
					isbnvo.setSeriesCode(rs.getString(2));
				}
				else{
					isbnvo.setSeriesSeq("");
					isbnvo.setSeriesCode("");
				}
				
			}
			
			seriesCode = "" + isbnvo.getSeriesCode();
			if (!seriesCode.equals("null") && !seriesCode.isEmpty()){
				
				ResultSet rs1 = null;
				PreparedStatement pstmt = null;
				String sql2 = "select SERIES_DESCRIPTION from SERIES_AS400 where SERIES_CODE =?";
				pstmt= con.prepareStatement(sql2);
				pstmt.setString(1, seriesCode);
				rs1=pstmt.executeQuery();
				while(rs1.next())
				{
					isbnvo.setSeriesDescription(rs1.getString(1));
				}
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
		return isbnvo;
	}
		
	
	
}
