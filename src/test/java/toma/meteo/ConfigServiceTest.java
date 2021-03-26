package toma.meteo;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import toma.meteo.service.ConfigService;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
//@ActiveProfiles("profiltoma,prod")
public class ConfigServiceTest {
	
	Logger logger = LogManager.getLogger(ConfigServiceTest.class);

	/**
	 * Test de recuperation des proprietes de la config
	 * @throws Exception
	 */
	@Test
	public void getPropriete() throws Exception {
		final Properties prop = ConfigService.getConfig();
		
		logger.debug("arduino.host: "+ prop.getProperty("arduino.host"));
		logger.debug("arduino.port: "+ prop.getProperty("arduino.port"));
	}

}
