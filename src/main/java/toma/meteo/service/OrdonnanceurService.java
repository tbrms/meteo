package toma.meteo.service;

import toma.meteo.bean.BulletinMeteo;
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

	@Autowired
	BulletinMeteoService bulletinMeteoService;

	@Autowired
	ArduinoService arduinoService;

	private RestTemplate restTemplate = new RestTemplate();

	@Scheduled(fixedRate = 3600000)
	public void insertBulletinMeteo() {
		Optional<BulletinMeteo> bulletinMeteo = arduinoService.getBulletinMeteo();

		if(bulletinMeteo.isPresent()){
			bulletinMeteoService.ajouter(bulletinMeteo.get());
		}
	}

}