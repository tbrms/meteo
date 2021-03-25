package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.googlecode.jmapper.JMapper;
import org.springframework.test.context.ActiveProfiles;
import toma.meteo.bean.BulletinMeteo;
import toma.meteo.bean.ReleveMeteo;

@SpringBootTest
//@ActiveProfiles("profiltoma,prod")
public class JMapperTest {
	
	@Test
	public void mapperReleveMeteoToBulletinMeteo() {
		BigDecimal temperature = new BigDecimal(20.0).setScale(1, RoundingMode.HALF_UP);
		//BigDecimal temperatureControle = new BigDecimal(temperature);
		int pression = 1014;
		int humidite = 50;
		ReleveMeteo releveMeteo = new ReleveMeteo(temperature, pression, humidite);
		
		JMapper<BulletinMeteo,ReleveMeteo> mapper = 
				new JMapper<BulletinMeteo, ReleveMeteo>(BulletinMeteo.class, ReleveMeteo.class);
		
		BulletinMeteo bulletinMeteo = mapper.getDestination(releveMeteo);
		
		assertNotNull(bulletinMeteo);
		assertEquals(temperature, bulletinMeteo.getTemperature());
		assertEquals(pression, bulletinMeteo.getPression());
		assertEquals(humidite, bulletinMeteo.getHumidite());
	}
}