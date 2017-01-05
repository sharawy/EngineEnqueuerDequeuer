package asset.customerEnquerDequer.threads;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.models.Transaction;
import asset.customerEnquerDequer.resourceManagers.OracleAQClient;
import asset.customerEnquerDequer.services.QueueService;

public class EnqueuingThread extends Thread {
	private static final Logger logger = Logger.getLogger(EnqueuingThread.class);

	private BlockingQueue<Transaction> customersObjects;
	private QueueService queueService;
	
	
	public EnqueuingThread(BlockingQueue<Transaction> customersObjects) {
		this.customersObjects = customersObjects;
		OracleAQClient.getInstance();
		queueService = new QueueService();
	}

public void run(){
		
		while(Defines.shutdownFlag){
			if (!customersObjects.isEmpty()){
				
				queueService.enqueueMessages(customersObjects);
			}
			else{
				try {
					Thread.sleep( Long.parseLong( Defines.properties.get(Defines.enqueingThreadSleepTime) ) );
				} catch (InterruptedException e) {
					
					logger.error(e.getStackTrace());
				}
			}
			
		}
}
}
