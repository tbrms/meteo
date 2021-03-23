package toma.meteo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    public ConfigService() {
    }

    private static final String CHEMIN_CONFIG = "src/main/resources/config.properties";
	//private static final String CHEMIN_CONFIG = "src/main/webapp/WEB-INF/config.properties";
	
	private static Logger logger = LogManager.getLogger(ConfigService.class);

	public static Properties readPropertiesFile(String fileName) throws Exception {
		File propertieFile = new File(fileName);
		logger.error("Chemin complet du fichier : " + propertieFile.getAbsolutePath());

		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);

			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		} finally {
			fis.close();
		}
		return prop;
	}
	
	public static Properties getConfig() throws Exception   {
		return readPropertiesFile(CHEMIN_CONFIG);
	}

}
