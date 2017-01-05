package asset.customerEnquerDequer.commons;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author abdalrahman.sharawy
 *
 */
public class Utils {
	static final Logger logger = Logger.getLogger(Utils.class);

	/**
	 * load engine properties
	 */
	public static void loadProperties(String propertiesFile) {
		logger.info("Loading properties.");
		ResourceBundle resourseBundle = ResourceBundle.getBundle(propertiesFile);

		// Get all keys in resource bundle
		Enumeration<?> keys = resourseBundle.getKeys();
		// save each key-value pair to the hashmap
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			String value = resourseBundle.getString((String) key).trim();
			Defines.properties.put(key.toString(), value);
		}
		logger.info("Done Loading properties.");
	}

	

}
