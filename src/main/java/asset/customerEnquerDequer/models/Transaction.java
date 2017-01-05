package asset.customerEnquerDequer.models;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author abdalrahman.sharawy
 *
 */
@XmlRootElement
@XmlType(propOrder = {"msisdn", "balance", "rate_plan","transcation_id","entry_date"})
public class Transaction extends Model {

	private String msisdn; // unique 10 digits
	private int balance;//balance  max num
	private int rate_plan;//rate plan foregin key 
	private String entry_date;//entry date 
	private int transcation_id;//transaction id unique
	
	

	public Transaction() {
		this.entry_date = "";
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getRate_plan() {
		return rate_plan;
	}

	public void setRate_plan(int rate_plan) {
		this.rate_plan = rate_plan;
	}



	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public int getTranscation_id() {
		return transcation_id;
	}

	public void setTranscation_id(int transcation_id) {
		this.transcation_id = transcation_id;
	}

	
	public  String jaxbObjectToXML() {
		String xmlString = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Transaction.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out for debugging
            // m.marshal(trans, System.out);
             //write to string variable
             StringWriter sw = new StringWriter();
             m.marshal(this, sw);
             xmlString = sw.toString();
           
             // Write to File
           // m.marshal(emp, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlString;
    }
	
	public  Transaction jaxbXMLToObject(String stringXmlObject) {
		 Transaction trans =null;   
		 try {
	            JAXBContext context = JAXBContext.newInstance(Transaction.class);
	            Unmarshaller un = context.createUnmarshaller();
	            StringReader reader = new StringReader(stringXmlObject);
	             trans = (Transaction) un.unmarshal(reader);
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
         return trans;

	    }
}
