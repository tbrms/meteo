package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.bean.ReleveMeteo;
import toma.meteo.dto.TemperatureDto;
import toma.meteo.utils.DateUtils;

@SpringBootTest
public class ModelMapperTest {
	
	@Autowired
	ModelMapper mapper;
	
	/*
	 * Test du mapping d'une ReleveMeteo en BulletinMeteo
	 */
	@Test
	public void mapperReleveMeteoToBulletinMeteo() {
		// On teste l'arrondi superieur
		float temperature = 20.07f;
		float pression = 1014.7f;
		float humidite = 50.7f;
		
		BigDecimal temperatureDestination = new BigDecimal("20.1")
				.setScale(1, RoundingMode.HALF_UP);
		int pressionDestination = 1015;
		int humiditeDestination = 51;
		
		ReleveMeteo releveMeteo = new ReleveMeteo(temperature, pression, humidite);
		
		BulletinMeteoExt bulletinMeteo = mapper.map(releveMeteo, BulletinMeteoExt.class);
		
		assertNotNull(bulletinMeteo);
		assertEquals(temperatureDestination, bulletinMeteo.getTemperature());
		assertEquals(pressionDestination, bulletinMeteo.getPression());
		assertEquals(humiditeDestination, bulletinMeteo.getHumidite());
		
		// On teste l'arrondi de 0.5
		temperature = 20.05f;
		pression = 1014.5f;
		humidite = 50.5f;
		
		temperatureDestination = new BigDecimal("20.1");
		temperatureDestination = temperatureDestination.setScale(1, RoundingMode.HALF_UP);
		pressionDestination = 1015;
		humiditeDestination = 51;
		
		releveMeteo = new ReleveMeteo(temperature, pression, humidite);
		
		bulletinMeteo = mapper.map(releveMeteo, BulletinMeteoExt.class);
		
		assertNotNull(bulletinMeteo);
		assertEquals(temperatureDestination, bulletinMeteo.getTemperature());
		assertEquals(pressionDestination, bulletinMeteo.getPression());
		assertEquals(humiditeDestination, bulletinMeteo.getHumidite());
		
		// On teste l'arrondi inferieur
		temperature = 20.02f;
		pression = 1014.2f;
		humidite = 50.2f;
		
		temperatureDestination = new BigDecimal("20.0");
		temperatureDestination = temperatureDestination.setScale(1, RoundingMode.HALF_UP);
		pressionDestination = 1014;
		humiditeDestination = 50;
		
		releveMeteo = new ReleveMeteo(temperature, pression, humidite);
		
		bulletinMeteo = mapper.map(releveMeteo, BulletinMeteoExt.class);
		
		assertNotNull(bulletinMeteo);
		assertEquals(temperatureDestination, bulletinMeteo.getTemperature());
		assertEquals(pressionDestination, bulletinMeteo.getPression());
		assertEquals(humiditeDestination, bulletinMeteo.getHumidite());
	}
	
	@Test
	void mapperBulletinMeteoExtToTemperatureDto() {
		BulletinMeteoExt bulletinMeteoExt = new BulletinMeteoExt();
		TemperatureDto temperatureDto = new TemperatureDto();
		
		LocalDateTime date = LocalDateTime.now();
		BigDecimal temperature = new BigDecimal("20.5");
		
		bulletinMeteoExt.setDate(date);
		bulletinMeteoExt.setTemperature(temperature);
		
		mapper.map(bulletinMeteoExt,temperatureDto);
		
		assertEquals(date.format(DateUtils.FORMATTER_COURT),temperatureDto.getName());
		assertEquals(temperature, temperatureDto.getValue());
	}
}