package toma.meteo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import toma.meteo.service.BulletinMeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.googlecode.jmapper.JMapper;
import toma.meteo.bean.BulletinMeteo;
import toma.meteo.bean.ReleveMeteo;

@RestController
public class ReleveMeteoController {
	
	Logger logger = LoggerFactory.getLogger(ReleveMeteoController.class);
	
	private ReleveMeteo releveMeteo;

	@Autowired
	BulletinMeteoService releveMeteoService;
	
	public ReleveMeteoController(ReleveMeteo releveMeteo) {
		this.releveMeteo = releveMeteo;
	}
	
	@GetMapping("/getReleveMeteo")
	public ReleveMeteo getReleveMeteo() {
		releveMeteo.setTemperature(getRandomTemp());
		releveMeteo.setPression(getRandomPression());
		releveMeteo.setHumidite(getRandomHumidite());
		return releveMeteo;
	}
	
	@PostMapping("/insertReleveMeteo")
	public void create(@RequestBody ReleveMeteo releveMeteo) {
		logger.debug("Releve Meteo: "+releveMeteo.toString());
		
		JMapper<BulletinMeteo,ReleveMeteo> mapper = 
				new JMapper<BulletinMeteo, ReleveMeteo>(BulletinMeteo.class, ReleveMeteo.class);

		BulletinMeteo bulletinMeteo = mapper.getDestination(releveMeteo);

		bulletinMeteo.setDate(new Date());

		releveMeteoService.ajouter(bulletinMeteo);
	}
	
	private BigDecimal getRandomTemp() {
		Random r = new Random();
		float random = r.nextInt(20) + 10;
		return new BigDecimal(random);
	}
	
	private float getRandomPression() {
		Random r = new Random();
		return (float) r.nextInt(100) + 950;
	}
	
	private float getRandomHumidite() {
		Random r = new Random();
		return (float) r.nextInt(100);
	}
}
