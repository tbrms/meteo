package toma.meteo.batch;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import toma.meteo.bean.BulletinMeteoExt;

public class BoucleProcessor 
implements ItemProcessor<Date,Date>{
	
	Logger logger = LogManager.getLogger(BoucleProcessor.class);

	@Override
	public Date process(Date date) throws Exception {
		logger.debug("Passage dans le processor - Thread en pause pour 1s");
		logger.debug("Date: " + date.toString());
		Thread.sleep(1000);
		return date;
	}

}
