package toma.meteo.batch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import toma.meteo.bean.BulletinMeteoExt;

public class ExportCsvBulletinMeteoExtProcessor 
implements ItemProcessor<BulletinMeteoExt,BulletinMeteoExt>{
	
	Logger logger = LogManager.getLogger(ExportCsvBulletinMeteoExtProcessor.class);

	@Override
	public BulletinMeteoExt process(BulletinMeteoExt bulletinMeteoExt) throws Exception {
		logger.debug("Process de: " + bulletinMeteoExt.toString());
		return bulletinMeteoExt;
	}

}
