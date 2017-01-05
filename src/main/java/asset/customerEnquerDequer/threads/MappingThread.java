package asset.customerEnquerDequer.threads;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Constants;
import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.models.Transaction;

public class MappingThread extends Thread{
	private static final Logger logger = Logger.getLogger(MappingThread.class);

    private  BlockingQueue<String []> customersCommaSeprated;
    private  BlockingQueue<Transaction> customersObjects;
    
	public MappingThread (BlockingQueue<String[]> customersCommaSeprated,BlockingQueue<Transaction> customersObjects){
		super();
		this.customersCommaSeprated= customersCommaSeprated;	
		this.customersObjects = customersObjects;
	}
	public void run(){
		
		while(Defines.shutdownFlag){
			
			if(!customersCommaSeprated.isEmpty()){
				
				Transaction trans = new Transaction();
				String [] temp = customersCommaSeprated.poll();
				trans.setBalance(Integer.parseInt(temp[Constants.balanceIndex]));
				trans.setMsisdn(temp[Constants.mssisdnIndex]);
				trans.setRate_plan(Integer.parseInt(temp[Constants.ratePlanIndex]));
				trans.setTranscation_id(Integer.parseInt(temp[Constants.transcationIdIndex]));
				
				try {
					this.customersObjects.put(trans);
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace());
				}
			}
			else{
				try {
					Thread.sleep( Long.parseLong( Defines.properties.get(Defines.mappingThreadSleepTime) ) );
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace());
				}
			}
			
		}
		
	}
}
