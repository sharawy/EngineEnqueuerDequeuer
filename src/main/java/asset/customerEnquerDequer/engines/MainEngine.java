package asset.customerEnquerDequer.engines;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Constants;
import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.commons.Utils;
import asset.customerEnquerDequer.models.Transaction;
import asset.customerEnquerDequer.threads.DequeingThread;
import asset.customerEnquerDequer.threads.EnqueuingThread;
import asset.customerEnquerDequer.threads.MappingThread;
import asset.customerEnquerDequer.threads.ReaderThread;

public class MainEngine {
	static final Logger logger = Logger.getLogger(MainEngine.class);
	static BlockingQueue<String[]> customersCommaSeprated ;
	static BlockingQueue<Transaction> customersObjects;
	static List<ReaderThread> readerThreadList = new ArrayList<ReaderThread>();
	static List<MappingThread> mappingThreadList = new ArrayList<MappingThread>();
	static List<EnqueuingThread> enqueuingThreadList = new ArrayList<EnqueuingThread>();
	static List<DequeingThread> dequeingThreadList = new ArrayList<DequeingThread>();
	
	

	public static void main(String[] args) throws IOException, SQLException, PropertyVetoException {
		logger.info("Starting Main Engine.");
		
		Utils.loadProperties(Constants.propertiesFile);
		 customersCommaSeprated = 
				new ArrayBlockingQueue<String[]>( Integer.parseInt( Defines.properties.get(Defines.customersCommaSepratedQueueSize) ) );
		 customersObjects = 
				new ArrayBlockingQueue<Transaction>(Integer.parseInt( Defines.properties.get(Defines.customersObjectsQueueSize) ));
		startEngine();
		
		logger.info("Main Engine Started Succesfully.");
		while(Defines.shutdownFlag){
			refreshThreads();
		}
	}

	static void startEngine() throws IOException, SQLException, PropertyVetoException {
		
		logger.info("Starting Threads.");
		
		for (int i = 0; i < Integer.parseInt(Defines.properties.get(Defines.readerThreadsNo)); i++) {
			ReaderThread r = new ReaderThread(Defines.properties.get(Defines.customerPath), customersCommaSeprated);
			r.start();
			readerThreadList.add(r);
		}
		
		
		for (int i = 0; i < Integer.parseInt(Defines.properties.get(Defines.mappingThreadsNo)); i++) {
			MappingThread m = new MappingThread(customersCommaSeprated, customersObjects);
			m.start();
			mappingThreadList.add(m);
		}
		
		for (int i = 0; i < Integer.parseInt(Defines.properties.get(Defines.enqueuingThreadsNo)); i++) {
			EnqueuingThread en = new EnqueuingThread(customersObjects);
			en.start();
			enqueuingThreadList.add(en);
		}
		
		for (int i = 0; i < Integer.parseInt(Defines.properties.get(Defines.dequeingThreadsNo)); i++) {
			DequeingThread de = new DequeingThread();
			de.start();
			dequeingThreadList.add(de);
		}
		
		logger.info("All Threads are Started.");
	}
	
	static void refreshThreads() throws IOException, SQLException, PropertyVetoException {
	
//		
//		for (int i = 0; i < readerThreadList.size(); i++) {
//			if (!readerThreadList.get(i).isAlive()) {
//				ReaderThread r = new ReaderThread(Defines.properties.get(Defines.customerPath), customersCommaSeprated);
//				r.start();
//				readerThreadList.remove(i);
//				readerThreadList.add(r);
//			}
//		}
		for (int i = 0; i < mappingThreadList.size(); i++) {
			
			if (!mappingThreadList.get(i).isAlive()) {
				logger.info("Reader Thread is dead restarting.");
				MappingThread m = new MappingThread(customersCommaSeprated, customersObjects);
				m.start();
				mappingThreadList.remove(i);
				mappingThreadList.add(m);
				logger.info("Reader Thread started.");
			
			}
		}
		for (int i = 0; i < enqueuingThreadList.size(); i++) {
			if (!enqueuingThreadList.get(i).isAlive()) {
				logger.info("enqueing Thread is dead restarting.");
				EnqueuingThread en = new EnqueuingThread(customersObjects);
				en.start();
				enqueuingThreadList.remove(i);
				enqueuingThreadList.add(en);
				logger.info("enqueing Thread started.");
			
			}
		}
		for (int i = 0; i < dequeingThreadList.size(); i++) {
			
			if (!dequeingThreadList.get(i).isAlive()) {
				logger.info("dequeing Thread is dead restarting.");
				DequeingThread de = new DequeingThread();
				de.start();
				dequeingThreadList.remove(i);
				dequeingThreadList.add(de);
				logger.info("enqueing Thread started.");
			
			}
		}
	}

}
