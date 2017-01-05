package asset.customerEnquerDequer.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import asset.customerEnquerDequer.commons.Defines;

public class ReaderThread extends Thread {
	private static final Logger logger = Logger.getLogger(ReaderThread.class);
	private String filePath;
	private BlockingQueue<String[]> customersCommaSeprated;
	private Boolean  doneReading = false;
	public ReaderThread(String filePath, BlockingQueue<String[]> customersCommaSeprated) {
		super();
		this.filePath = filePath;
		this.customersCommaSeprated = customersCommaSeprated;
	}

	public void run() {
		logger.debug("Starting Reader Thread (id:"+Thread.currentThread().getName()+")");
		while (Defines.shutdownFlag) {

			BufferedReader br = null;
			FileReader fr = null;
			

			try {
				
				
				if (!doneReading) {
					File f = new File(this.filePath);

					fr = new FileReader(f);

					br = new BufferedReader(fr);

					String sCurrentLine;

					br = new BufferedReader(fr);
					logger.debug("Starting Reading file: "+this.filePath+" (thread id:"+Thread.currentThread().getName()+")");

					while ((sCurrentLine = br.readLine()) != null) {
						try {
							customersCommaSeprated.put(sCurrentLine.split(",", -1));
						} catch (InterruptedException e) {
							logger.error(e.getStackTrace());
						}

					}
					doneReading = true;
					logger.debug("Done Reading file: "+this.filePath+" (id:"+Thread.currentThread().getName()+")");
					
					
				}else{
					Thread.sleep( Long.parseLong( Defines.properties.get(Defines.readerThreadSleeptime) ) );
				}

			} catch (IOException e) {

				logger.error(e.getStackTrace());

			} catch (InterruptedException e) {
				
				logger.error(e.getStackTrace());
				
			} finally {
				try {
					
					if (br != null)
						br.close();

					if (fr != null)
						fr.close();
					
				} catch (IOException ex) {

					logger.error(ex.getStackTrace());

				}

			}
		}

	}

}
