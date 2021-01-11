package toma.meteo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toma.meteo.bean.BulletinMeteo;
import toma.meteo.repository.BulletinMeteoRepository;

import org.apache.commons.collections4.IterableUtils;

@Service
public class BulletinMeteoService {
	
	@Autowired
	private BulletinMeteoRepository bulletinMeteoRepository;

	public void ajouter(BulletinMeteo releveMeteo) {
		bulletinMeteoRepository.save(releveMeteo);
	}
	
	public void effacer(BulletinMeteo releveMeteo) {
		bulletinMeteoRepository.delete(releveMeteo);
	}
	
	public Optional<BulletinMeteo> lire(BulletinMeteo releveMeteo) {
		 return bulletinMeteoRepository.findById(releveMeteo.getId());
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
}
