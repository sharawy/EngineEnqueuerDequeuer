package asset.customerEnquerDequer.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Constants;
import asset.customerEnquerDequer.commons.Defines;


/**
 * @author abdalrahman.sharawy
 *
 */
public class QueueDao {
	private static final Logger logger = Logger.getLogger(QueueDao.class);

	
	/**
	 * get transaction queue size
	 * @param connection connection instance to db
	 * @return queue size
	 */
	public int getTransactionQueueSize(Connection connection ){
		ResultSet rs = null;
		PreparedStatement  stmt = null;
		int count = 0;
		try {
			stmt = connection.prepareStatement("select count(*) from "+Constants.queueTableName);
		
			
			 rs = stmt.executeQuery();
			 while (rs.next()) {
				count = rs.getInt(1);
			 }
		} catch (SQLException e) {
			logger.error(e.getStackTrace());
		}
		finally{
			
				try {
					if(stmt != null)
						stmt.close();
					if(rs != null)
						rs.close();
				} catch (SQLException e) {
					logger.error(e.getStackTrace());
				}
		}
		return count;
	}
	
	/**
	 * call dequeue messages procedure
	 * @param connection
	 */
	public void callDequeueMessages(Connection connection){
		CallableStatement cstmt = null;
		String command = "{ call deque_messages("+Defines.properties.get(Defines.numberOfmessages)+") }";
		
		try {
			cstmt = connection.prepareCall(command);
			cstmt.execute();
		} catch (SQLException e) {
			logger.error(e.getStackTrace());
		}
		
	}
}
