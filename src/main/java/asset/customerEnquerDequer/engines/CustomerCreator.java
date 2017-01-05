package asset.customerEnquerDequer.engines;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import asset.customerEnquerDequer.commons.Constants;
import asset.customerEnquerDequer.commons.Defines;
import asset.customerEnquerDequer.commons.Utils;

public class CustomerCreator {

	public static void main(String[] args) {
		Utils.loadProperties(Constants.propertiesFile);
		FileWriter writer = null;
		Random rand = new Random();
		StringBuffer buf = new StringBuffer();
		
			try {
				for (int i = 0; i<1000000;i++){
					buf.append(rand.nextInt(900000000) + 1000000000).append(',')
					.append(rand.nextInt(9) + 10).append(",")
					.append(rand.nextInt((3 - 1) + 1) + 1).append(',')
					.append(rand.nextInt(999999999))
					.append(System.getProperty("line.separator"));
					///System.out.println(buf.toString());
			
			
				}
				System.out.println("finished");
				writer = new FileWriter(Defines.properties.get(Defines.customerPath),true);
				writer.write(buf.toString());
				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			finally {
				try {
					if(writer != null)
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}

}
