package toma.meteo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toma.meteo.bean.BulletinMeteo;
import toma.meteo.repository.BulletinMeteoRepository;

import org.apache.commons.collections4.IterableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class BulletinMeteoService {
	
	@Autowired
	private BulletinMeteoRepository bulletinMeteoRepository;
	
	private static Logger logger = LogManager.getLogger(BulletinMeteoService.class);

	public void ajouter(BulletinMeteo bulletinMeteo) {
		logger.debug("Ajout en BDD: " + bulletinMeteo.toString());
		bulletinMeteoRepository.save(bulletinMeteo);
	}
	
	public void effacer(BulletinMeteo bulletinMeteo) {
		bulletinMeteoRepository.delete(bulletinMeteo);
	}
	
	public Optional<BulletinMeteo> lire(BulletinMeteo bulletinMeteo) {
		 return bulletinMeteoRepository.findById(bulletinMeteo.getId());
	}
	public BulletinMeteo lireByDate(Date date) {
		return bulletinMeteoRepository.findByDate(date);
	}

	public List<BulletinMeteo> lireTous() {
		//FIXME adapter iterable vers list
		return IterableUtils.toList(bulletinMeteoRepository.findAll());
	}

	public List<BulletinMeteo> lireEntreDate(Date dateDebut, Date dateFin){
		return bulletinMeteoRepository.findAllByDateBetween(dateDebut, dateFin);
	}

	public BulletinMeteo getLast(){
		return bulletinMeteoRepository.findFirstByOrderByDateDesc();
	}
	
	public List<BulletinMeteo> getDerniersBulletins(int n){
		return bulletinMeteoRepository.getDerniersBulletins(n);
	}
}
