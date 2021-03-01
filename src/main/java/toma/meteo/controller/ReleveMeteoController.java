package toma.meteo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import toma.meteo.service.ArduinoService;
import toma.meteo.service.BulletinMeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@Autowired
	ArduinoService arduinoService;
	
	public ReleveMeteoController(ReleveMeteo releveMeteo) {
		this.releveMeteo = releveMeteo;
	}
	
	/*
	 * Recuperer un bulletin meteo fictif
	 */
	@GetMapping("/getReleveMeteo")
	public ReleveMeteo getReleveMeteo() {
		releveMeteo.setTemperature(getRandomTemp());
		releveMeteo.setPression(getRandomPression());
		releveMeteo.setHumidite(getRandomHumidite());
		return releveMeteo;
	}
	
	/*
	 * Recuperer le dernier bulletin meteo en BDD
	 */
	@GetMapping("/getLastBulletinMeteo")
	public BulletinMeteo getLastReleveMeteo() {
		return releveMeteoService.getLast();
	}
	
	/*
	 * Recuperer les n derniers bulletins en BDD
	 */
	@GetMapping(value = "/releves/{n}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BulletinMeteo> getDerniersBulletins(@PathVariable("n") int n) {
		return releveMeteoService.getDerniersBulletins(n);
	}
	
	/*
	 * Recuperer un bulletin meteo en direct
	 */
	//@CrossOrigin(origins = "*")
	@GetMapping("/getBulletinMeteoNow")
	public BulletinMeteo getReleveMeteoNow() {
		Optional<BulletinMeteo> bulletin = arduinoService.getBulletinMeteo();
		if (bulletin.isPresent()) {
			return bulletin.get();
		} else {
			return null;
		}
	}
	
	/*
	 * Inserer un bulletin meteo en BDD
	 */
	@PostMapping("/insertReleveMeteo")
	public void create(@RequestBody ReleveMeteo releveMeteo) {
		logger.debug("Releve Meteo: "+releveMeteo.toString());
		
		JMapper<BulletinMeteo,ReleveMeteo> mapper = 
				new JMapper<BulletinMeteo, ReleveMeteo>(BulletinMeteo.class, ReleveMeteo.class);

		BulletinMeteo bulletinMeteo = mapper.getDestination(releveMeteo);

		bulletinMeteo.setDate(new Date());

		releveMeteoService.ajouter(bulletinMeteo);
	}
	
	/*
	 * Recuperer une temperature fictive
	 */
	private BigDecimal getRandomTemp() {
		Random r = new Random();
		float random = r.nextInt(20) + 10;
		return new BigDecimal(random);
	}
	
	/*
	 * Recuperer une pression fictive
	 */
	private float getRandomPression() {
		Random r = new Random();
		return (float) r.nextInt(100) + 950;
	}
	
	/*
	 * Recuperer une humidite fictive
	 */
	private float getRandomHumidite() {
		Random r = new Random();
		return (float) r.nextInt(100);
	}
}
