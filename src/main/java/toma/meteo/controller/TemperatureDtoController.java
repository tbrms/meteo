package toma.meteo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.dto.donnee.TemperatureDto;
import toma.meteo.dto.series.TemperatureDtoSeries;
import toma.meteo.service.BulletinMeteoService;

@RestController
@Api(value = "TemperatureDtoController")
public class TemperatureDtoController {
	
	Logger logger = LogManager.getLogger(TemperatureDtoController.class);
	
	@Autowired
	ModelMapper mapper;

	@Autowired
	BulletinMeteoService bulletinMeteoService;
	
	/*
	 * Obtenir les n dernieres temperatures avec une date formatee
	 * pour affichage par le front end
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Obtenir les n dernieres temperatures les plus recentes en BDD", response = TemperatureDtoSeries.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Liste des n dernieres temperatures envoyée"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@GetMapping(value = "/temperatureSeries/{n}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TemperatureDtoSeries getDerniersBulletins(@PathVariable("n") int n) {
		//FIXME Ameliorer le code en java 8
		List<BulletinMeteoExt> listeBulletinMeteoExt = 
				bulletinMeteoService.getDerniersBulletins(n);
		listeBulletinMeteoExt.sort(Comparator.comparing(BulletinMeteoExt::getDate));
		TemperatureDtoSeries temperatureDtoSeries = new TemperatureDtoSeries();
		List<TemperatureDto> listeTemperatureDto = new ArrayList<TemperatureDto>();
		
		for(BulletinMeteoExt element : listeBulletinMeteoExt) {
			TemperatureDto temperatureDto = new TemperatureDto();
			mapper.map(element, temperatureDto);
			listeTemperatureDto.add(temperatureDto);
		}
		
		temperatureDtoSeries.setSeries(listeTemperatureDto);
		temperatureDtoSeries.setName("Temperature");
		
		return temperatureDtoSeries;
	}

}
