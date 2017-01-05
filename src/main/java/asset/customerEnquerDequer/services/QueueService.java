package asset.customerEnquerDequer.services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import asset.customerEnquerDequer.commons.Constants;
import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.daos.QueueDao;
import asset.customerEnquerDequer.models.Transaction;
import asset.customerEnquerDequer.resourceManagers.OracleAQClient;

public class QueueService {

	// get count
	/**
	 * @param connection
	 * @return queue size
	 */
	public int getTransactionQueueSize(Connection connection) {
		QueueDao queueDao = new QueueDao();
		return queueDao.getTransactionQueueSize(connection);
	}

	// send message to queue
	/**
	 * @param customersObjects array blocking queue of transaction model 
	 */
	public void enqueueMessages(BlockingQueue<Transaction> customersObjects) {

		OracleAQClient oracleAQClient = OracleAQClient.getInstance();
		Transaction tempTransaction;
		List<String> xmlCustomers = new ArrayList<String>(
				Integer.parseInt(Defines.properties.get(Defines.xmlCustomersListSize)));
		for (int i = 0; i < customersObjects.size(); i++) {
			tempTransaction = new Transaction();
			tempTransaction = customersObjects.poll();
			xmlCustomers.add(tempTransaction.jaxbObjectToXML());
		}
		oracleAQClient.sendMessage(Constants.queueName, xmlCustomers);

	}

	
	/**
	 * call dequeue messages procedure
	 * @param connection
	 * 
	 */
	public void callDequeueMessages(Connection connection) {
		QueueDao queueDao = new QueueDao();
		queueDao.callDequeueMessages(connection);
	}
}
