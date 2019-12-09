import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class workDAO {
	
	private ResultSet rs=null;
	private PreparedStatement pstmt= null;
	
	public workVO getparentID(Connection con, String isbn10) {
		workVO workvo = new workVO();
		try {
			String sql = "SELECT w.PARENT_ID,w.EDITION_VOLUME,w.TITLE FROM WORK w, ISBN i where i.WORK_ID = w.WORK_ID and i.ISBN = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn10);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				String temp = rs.getString(1);
				if (!temp.equals("0") && !temp.equals("null") && !temp.isEmpty()){
					
					workvo.setParentId(rs.getString(1));
					workvo.setEditionVolume(rs.getString(2));
					workvo.setTitle(rs.getString(3));
				}else{
					workvo.setParentId("");
					
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
		return workvo;
	}

}
