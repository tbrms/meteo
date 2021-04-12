package toma.meteo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.bean.ReleveMeteo;
import toma.meteo.service.ArduinoService;
import toma.meteo.service.BulletinMeteoService;

@RestController
@Api(value = "ReleveMeteoController")
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
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Obtenir le releve meteo fictif", response = ReleveMeteo.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Relevé envoyé"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@GetMapping("/getReleveMeteo")
	public ReleveMeteo getReleveMeteo() {
		releveMeteo.setTemperature(getRandomTemp());
		releveMeteo.setPression(getRandomPression());
		releveMeteo.setHumidite(getRandomHumidite());
		return releveMeteo;
	}
	
	/*
	 * Inserer un bulletin meteo en BDD
	 */
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Insérer un relevé en BDD", response = BulletinMeteoExt.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Relevé inséré"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
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
