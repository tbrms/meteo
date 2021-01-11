package toma.meteo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    public ConfigService() {
    }

    private static final String CHEMIN_CONFIG = "src/main/resources/config.properties";
	//private static final String CHEMIN_CONFIG = "src/main/webapp/WEB-INF/config.properties";
	
	private static Logger logger = LoggerFactory.getLogger(ConfigService.class);

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
