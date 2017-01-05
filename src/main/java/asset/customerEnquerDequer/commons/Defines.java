package asset.customerEnquerDequer.commons;

import java.util.HashMap;

public  class Defines {
	//db authentication keys
	public static final String dbUrl= "dbUrl";
	public static final String userName= "userName";
	public static final String password= "password";
	public static final String driverUrl= "driverUrl";
	public static final String customerPath = "customerPath"; // TODO make it relative  
	public static final String oracleSid = "oracleSid";
	public static final String portNo = "portNo";
	public static final String driver = "driver";
	public static final String hostName = "hostName";

	
	public static final String minPoolSize= "minPoolSize";
	public static final String maxPoolSize= "maxPoolSize";
	public static final String acquireIncrement= "acquireIncrement";
	public static final String maxStatements= "maxStatements";
	
	
	
	//threads config keys
	public static final String customersCommaSepratedQueueSize= "customersCommaSepratedQueueSize";
	public static final String customersObjectsQueueSize= "customersObjectsQueueSize";
	
	public static final String readerThreadsNo= "readerThreadsNo";
	public static final String readerThreadSleeptime= "readerThreadSleeptime";

	public static final String mappingThreadsNo= "mappingThreadsNo";
	public static final String mappingThreadSleepTime= "mappingThreadSleepTime";

	public static final String enqueuingThreadsNo= "enqueuingThreadsNo";
	public static final String enqueingThreadSleepTime= "enqueingThreadSleepTime";
	public static final String xmlCustomersListSize = "xmlCustomersListSize";
	
	public static final String dequeingThreadsNo= "dequeingThreadsNo";
	public static final String dequeingThreadSleepTime= "dequeingThreadSleepTime";
	public static final String numberOfmessages = "numberOfmessages";
	

	
	
	//engine config
	public static boolean shutdownFlag = true;
	public static HashMap<String, String> properties = new HashMap<String, String>();

	
	
	
	
}
