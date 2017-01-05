package asset.customerEnquerDequer.models;

/**
 * @author abdalrahman.sharawy
 *
 */
public abstract class Model {
	
	/**
	 * convert model object to XML string
	 * @return  XML string
	 */
	public abstract  String jaxbObjectToXML();
	
	/**
	 * @param stringXmlObject XML string with object data
	 * @return Model object
	 */
	public abstract  Model jaxbXMLToObject(String stringXmlObject);
}
