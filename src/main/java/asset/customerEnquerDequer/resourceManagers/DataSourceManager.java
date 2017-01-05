package asset.customerEnquerDequer.resourceManagers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import asset.customerEnquerDequer.commons.Defines;

public class DataSourceManager {
	private static final Logger logger = Logger.getLogger(DataSourceManager.class);
	 private static DataSourceManager datasource;
	  private ComboPooledDataSource cpds;
	  HashMap<String,String> prop;

	  private DataSourceManager(HashMap<String,String> prop) throws IOException, SQLException, PropertyVetoException {
	    this.prop = prop;
	   
	    cpds = new ComboPooledDataSource();
	    cpds.setDriverClass(prop.get(Defines.driverUrl)); //loads the jdbc driver
	    cpds.setJdbcUrl(prop.get(Defines.dbUrl));
	    cpds.setUser(prop.get(Defines.userName));
	    cpds.setPassword(prop.get(Defines.password));

	    // the settings below are optional -- c3p0 can work with defaults
	    cpds.setMinPoolSize(Integer.parseInt(prop.get(Defines.minPoolSize)));
	    cpds.setAcquireIncrement(Integer.parseInt(prop.get(Defines.acquireIncrement)));
	    cpds.setMaxPoolSize(Integer.parseInt(prop.get(Defines.maxPoolSize)));
	    cpds.setMaxStatements(Integer.parseInt(prop.get(Defines.maxStatements)));

	  }

	  public static DataSourceManager getInstance(HashMap<String,String> prop) throws IOException, SQLException, PropertyVetoException {
	    if (datasource == null) {
	      datasource = (DataSourceManager) new DataSourceManager(prop);
	      return datasource;
	    } else {
	      return datasource;
	    }
	  }

	  public  Connection getConnection() throws SQLException  {
		  logger.debug("acquire a connection");
	    return this.cpds.getConnection();
	  }
}
