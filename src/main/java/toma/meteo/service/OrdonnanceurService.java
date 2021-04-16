package toma.meteo.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import toma.meteo.bean.BulletinMeteoExt;

@Service
public class OrdonnanceurService {
	
	private static Logger logger = LogManager.getLogger(OrdonnanceurService.class);

	@Autowired
	BulletinMeteoService bulletinMeteoService;

	@Autowired
	ArduinoService arduinoService;

	/**
	 * Inserer regulierement un releve de l'Arduino en BDD
	 */
	@Scheduled(cron = "${cron.expression}")
	public void insertBulletinMeteo() {
		Optional<BulletinMeteoExt> bulletinMeteo = arduinoService.getBulletinMeteo();
		logger.debug("Recuperation d'un releve depuis Arduino: " + bulletinMeteo.toString());
		if(bulletinMeteo.isPresent()){
			bulletinMeteoService.ajouter(bulletinMeteo.get());
		}
	}

}
