package asset.customerEnquerDequer.threads;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.resourceManagers.DataSourceManager;
import asset.customerEnquerDequer.services.QueueService;

public class DequeingThread extends Thread {
	private static final Logger logger = Logger.getLogger(DequeingThread.class);

	private DataSourceManager datasource;
	private Connection connection;
	QueueService queueService;

	public DequeingThread() throws IOException, SQLException, PropertyVetoException {
		datasource = DataSourceManager.getInstance(Defines.properties);
		queueService = new QueueService();
	}

	public void run() {

		while (Defines.shutdownFlag) {
			
				try {
					connection = datasource.getConnection();
					logger.debug("queue table Size : "+ queueService.getTransactionQueueSize(connection) );
					logger.debug("Call stored Procedure dequeu messages with read count of :"+Defines.properties.get(Defines.numberOfmessages));
					
					// TODO: procedure return number of the skipped messages 
					queueService.callDequeueMessages(connection);
					
					logger.debug("procedure executed");
					
				} catch (SQLException e) {
					logger.error(e.getStackTrace());
				} finally {

					try {
					

						if (connection != null)
							connection.close();
						
					} catch (SQLException e) {
						logger.error(e.getStackTrace());
					}

				}
			
				try {
					Thread.sleep( Long.parseLong( Defines.properties.get(Defines.dequeingThreadSleepTime) ) );
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace());
				
			}
		}
	}

}
