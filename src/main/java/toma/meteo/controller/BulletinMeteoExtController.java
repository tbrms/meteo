package toma.meteo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.service.ArduinoService;
import toma.meteo.service.BulletinMeteoService;

@RestController
@Api(value = "BulletinMeteoExtController")
public class BulletinMeteoExtController {
	
	Logger logger = LogManager.getLogger(BulletinMeteoExtController.class);
	
	@Autowired
	ModelMapper mapper;

	@Autowired
	BulletinMeteoService bulletinMeteoService;
	
	@Autowired
	ArduinoService arduinoService;
	
	public BulletinMeteoExtController() {
	}
	
	/*
	 * Recuperer le dernier bulletin meteo en BDD
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Obtenir le bulletin meteo le plus recent en BDD", response = BulletinMeteoExt.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Dernier bulletin envoyé"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@GetMapping("/getLastBulletinMeteo")
	public BulletinMeteoExt getLastReleveMeteo() {
		return bulletinMeteoService.getLast();
	}
	
	/*
	 * Recuperer les n derniers bulletins en BDD
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Obtenir les n derniers bulletins meteo le plus recent en BDD", response = BulletinMeteoExt.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Liste des n derniers bulletin envoyée"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@GetMapping(value = "/releves/{n}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BulletinMeteoExt> getDerniersBulletins(@PathVariable("n") int n) {
		return bulletinMeteoService.getDerniersBulletins(n);
	}
	
	/*
	 * Recuperer un bulletin meteo en direct
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Obtenir un bulletin météo instantané", response = BulletinMeteoExt.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Bulletin météo instantané"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@CrossOrigin(origins = "*")
	@GetMapping("/getReleveMeteoInstantane")
	public BulletinMeteoExt getReleveMeteoInstantane() {
		Optional<BulletinMeteoExt> bulletin = arduinoService.getBulletinMeteo();
		if (bulletin.isPresent()) {
			return bulletin.get();
		} else {
			return null;
		}
	}
}
