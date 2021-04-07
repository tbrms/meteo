package toma.meteo.service;

import toma.meteo.bean.BulletinMeteo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrdonnanceurService {
	
	//TODO A supprimer
	private static final String HOST="localhost";
	private static final String PORT="8081";
	private static final String CONTEXT="meteo";
	private static final String REQUEST="getReleveMeteo";
	
	private static Logger logger = LogManager.getLogger(OrdonnanceurService.class);

	@Autowired
	BulletinMeteoService bulletinMeteoService;

	@Autowired
	ArduinoService arduinoService;

	private RestTemplate restTemplate = new RestTemplate();

	//@Scheduled(cron = "0 0 * * * *")
	@Scheduled(cron = "${cron.expression}")
	public void insertBulletinMeteo() {
		Optional<BulletinMeteo> bulletinMeteo = arduinoService.getBulletinMeteo();
		logger.debug("Recuperation d'un releve depuis Arduino: " + bulletinMeteo.toString());
		if(bulletinMeteo.isPresent()){
			bulletinMeteoService.ajouter(bulletinMeteo.get());
		}
	}

}
