package toma.meteo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.bean.ReleveMeteo;
import toma.meteo.service.ArduinoService;
import toma.meteo.service.BulletinMeteoService;

@RestController
public class ReleveMeteoController {
	
	Logger logger = LogManager.getLogger(ReleveMeteoController.class);
	
	private ReleveMeteo releveMeteo;
	
	@Autowired
	ModelMapper mapper;

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
	public BulletinMeteoExt getLastReleveMeteo() {
		return releveMeteoService.getLast();
	}
	
	/*
	 * Recuperer les n derniers bulletins en BDD
	 */
	@GetMapping(value = "/releves/{n}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BulletinMeteoExt> getDerniersBulletins(@PathVariable("n") int n) {
		return releveMeteoService.getDerniersBulletins(n);
	}
	
	/*
	 * Recuperer un bulletin meteo en direct
	 */
	//@CrossOrigin(origins = "*")
	@GetMapping("/getBulletinMeteoNow")
	public BulletinMeteoExt getReleveMeteoNow() {
		Optional<BulletinMeteoExt> bulletin = arduinoService.getBulletinMeteo();
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
		
		BulletinMeteoExt bulletinMeteo = 
				this.mapper.map(releveMeteo, BulletinMeteoExt.class);

		bulletinMeteo.setDate(LocalDateTime.now());

		releveMeteoService.ajouter(bulletinMeteo);
	}
	
	/*
	 * Recuperer une temperature fictive
	 */
	private float getRandomTemp() {
		Random r = new Random();
		return (float) r.nextInt(20) + 10;
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
