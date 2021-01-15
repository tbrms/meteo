package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		Float temperature = 20.0f;
		Float pression = 1014.0f;
		Float humidite = 50.0f;
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
