package toma.meteo;

import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import toma.meteo.service.ConfigService;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
//@ActiveProfiles("profiltoma,prod")
public class ConfigServiceTest {
	
	Logger logger = LoggerFactory.getLogger(ConfigServiceTest.class);

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
