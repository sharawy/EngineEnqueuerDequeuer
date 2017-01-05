package asset.customerEnquerDequer.resourceManagers;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Defines;
import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;

public class OracleAQClient {
	private static final Logger logger = Logger.getLogger(OracleAQClient.class);
	private static OracleAQClient oracleAQClient;
	QueueConnectionFactory QFac = null;
	QueueConnection QCon = null;
	private String hostname;
	private String oracle_sid;
	private int portno;

	private String userName;
	private String password;
	private String driver;

	public OracleAQClient() {

		hostname = Defines.properties.get(Defines.hostName);
		oracle_sid = Defines.properties.get(Defines.oracleSid);
		portno = Integer.parseInt(Defines.properties.get(Defines.portNo));

		userName = Defines.properties.get(Defines.userName);
		password = Defines.properties.get(Defines.password);
		driver = Defines.properties.get(Defines.driver);
		try {
			QFac = AQjmsFactory.getQueueConnectionFactory(hostname, oracle_sid, portno, driver);
		} catch (JMSException e) {
			logger.error(e.getStackTrace());
		}
	}

	public static OracleAQClient getInstance() {
		if (oracleAQClient == null) {
			oracleAQClient = (OracleAQClient) new OracleAQClient();
			return oracleAQClient;
		} else {
			return oracleAQClient;
		}
	}

	private QueueConnection getConnection() {

		try {

			// create connection
			QCon = QFac.createQueueConnection(userName, password);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
		return QCon;
	}

	public void sendMessage(String queueName, List<String> messages) {
		Session session = null;
		MessageProducer producer = null;
		QueueConnection QCon = null;
		try {
			TextMessage tMsg;
			QCon = getConnection();
			session = QCon.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			QCon.start();
			Queue queue = ((AQjmsSession) session).getQueue(this.userName, queueName);
			producer = session.createProducer(queue);

			logger.debug("staring to send " + messages.size() + " message");
			for (String msg : messages) {
				tMsg = session.createTextMessage(msg);
				producer.send(tMsg);
			}
			logger.debug("done sending messages");

		} catch (JMSException e) {
			logger.error(e.getStackTrace());

		} finally {
			try {
				if (session != null)
					session.close();
				if (producer != null)
					producer.close();
				if (QCon != null)
					QCon.close();

			} catch (JMSException e) {
				logger.error(e.getStackTrace());
			}

		}
	}

}