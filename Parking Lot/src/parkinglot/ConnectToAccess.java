package parkinglot;

import java.sql.*;
import java.util.Properties;

public class ConnectToAccess {
	private Connection con=null;
	private ResultSet rs=null;
	private Statement stmtquery=null;
	private Statement stmtupdate=null;
	private PreparedStatement ptmt=null;
	private Properties pro=new Properties();
	private String url = "jdbc:Access:///ParkingLot.mdb";
	public ConnectToAccess() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
	{
		pro.setProperty("charSet", "GB2312");//ÉèÖÃÖÐÎÄ×Ö·û
	}
	public void Open() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
	    con = DriverManager.getConnection(url,pro);
	    stmtquery=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    stmtupdate=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}
	public void ReadyForPtmt(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		ptmt=con.prepareStatement(sql);
	}
	public PreparedStatement getPtmt() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		return ptmt;
	}
	public ResultSet executeQuery(String SQL) throws Exception
	{
		rs=stmtquery.executeQuery(SQL);
		return rs;
	}
	public void executeUpdate(String SQL) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		stmtupdate.executeUpdate(SQL);
	}
	public void close() throws SQLException
	{
		stmtquery.close();
		stmtupdate.close();
		con.close();
	}
}
