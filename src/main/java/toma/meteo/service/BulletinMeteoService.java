package toma.meteo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.repository.BulletinMeteoRepository;

@Service
public class BulletinMeteoService {
	
	@Autowired
	private BulletinMeteoRepository bulletinMeteoRepository;
	
	private static Logger logger = LogManager.getLogger(BulletinMeteoService.class);

	public void ajouter(BulletinMeteoExt bulletinMeteo) {
		logger.debug("Ajout en BDD: " + bulletinMeteo.toString());
		bulletinMeteoRepository.save(bulletinMeteo);
	}
	
	public void effacer(BulletinMeteoExt bulletinMeteo) {
		bulletinMeteoRepository.delete(bulletinMeteo);
	}
	
	public Optional<BulletinMeteoExt> lire(BulletinMeteoExt bulletinMeteo) {
		 return bulletinMeteoRepository.findById(bulletinMeteo.getId());
	}
	public BulletinMeteoExt lireByDate(LocalDateTime date) {
		return bulletinMeteoRepository.findByDate(date);
	}

	public List<BulletinMeteoExt> lireTous() {
		//FIXME adapter iterable vers list
		return IterableUtils.toList(bulletinMeteoRepository.findAll());
	}

	public List<BulletinMeteoExt> lireEntreDate(LocalDateTime dateDebut, LocalDateTime dateFin){
		return bulletinMeteoRepository.findAllByDateBetween(dateDebut, dateFin);
	}

	public BulletinMeteoExt getLast(){
		return bulletinMeteoRepository.findFirstByOrderByDateDesc();
	}
	
	public List<BulletinMeteoExt> getDerniersBulletins(int n){
		return bulletinMeteoRepository.getDerniersBulletins(n);
	}
}
