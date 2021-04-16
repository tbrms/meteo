package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.service.BulletinMeteoService;
import toma.meteo.utils.DateUtils;

@SpringBootTest
class BulletinMeteoServiceTest {

	private static final BigDecimal TEMP = 
			new BigDecimal(19.1).setScale(1, RoundingMode.HALF_UP);;
	private static final int PRESSION = 1014;
	private static final int HUMIDITE = 50;
	
	@Autowired
	BulletinMeteoService bulletinMeteoService;

	@Test
	void contextLoads() {
	}

	/**
	 * Test d'insertion d'une temperature dans la BDD
	 */
	@Test
	public void insererTemperature() {
		LocalDateTime date = LocalDateTime.now();

		BulletinMeteoExt bulletinMeteo = getBulletinMeteo(date);
		BulletinMeteoExt bulletinMeteoRetour = new BulletinMeteoExt();

		bulletinMeteoService.ajouter(bulletinMeteo);
		
		bulletinMeteoRetour = bulletinMeteoService.lireByDate(date);
		
		assertNotNull(bulletinMeteoRetour,"Le releve meteo est absent de la BDD");
		assertEquals(TEMP, bulletinMeteoRetour.getTemperature(),"Temperature differente");
		assertEquals(PRESSION, bulletinMeteoRetour.getPression(),"Pression differente");
		assertEquals(HUMIDITE, bulletinMeteoRetour.getHumidite(),"Humidite differente");
		
		bulletinMeteoService.effacer(bulletinMeteo);
		
		bulletinMeteoRetour = bulletinMeteoService.lireByDate(date);
		assertNull(bulletinMeteoRetour,"Le releve meteo n'a pas ete efface");
	}

	/**
	 * Test de recuperation des bulletins meteo entre deux dates
	 */
	@Test
	public void getBulletinMeteoBetweenDate(){
		
		LocalDateTime date = LocalDateTime.parse("2000-01-01,00:00:05",DateUtils.FORMATTER);
		LocalDateTime dateDebut = LocalDateTime.parse("2000-01-01,00:00:00",DateUtils.FORMATTER);
		LocalDateTime dateFin = LocalDateTime.parse("2000-01-01,00:00:10",DateUtils.FORMATTER);

		/*
		 * Date date =
		 * Date.from(localDateDebut.atZone(ZoneId.systemDefault()).toInstant()); Date
		 * dateDebut =
		 * Date.from(localDateDebut.atZone(ZoneId.systemDefault()).toInstant()); Date
		 * dateFin = Date.from(localDateFin.atZone(ZoneId.systemDefault()).toInstant());
*/
		BulletinMeteoExt bulletinMeteoExt = getBulletinMeteo(date);
		//bulletinMeteoExt.setDate(date);

		bulletinMeteoService.ajouter(bulletinMeteoExt);

		List<BulletinMeteoExt> listeBulletin = bulletinMeteoService.lireEntreDate(dateDebut, dateFin);

		assertNotNull(listeBulletin, "La liste est null");
		assertEquals(1,listeBulletin.size(), "La taille de la liste est differente de 1");
		assertEquals(date, listeBulletin.get(0).getDate(), "La date n'est pas correcte");

		bulletinMeteoService.effacer(bulletinMeteoExt);
	}

	/**
	 * Creation d'un bulletin meteo
	 * @return le bulletin meteo cree
	 */
	private BulletinMeteoExt getBulletinMeteo(LocalDateTime date) {
		BulletinMeteoExt bulletinMeteoExt = new BulletinMeteoExt();

		bulletinMeteoExt.setDate(date);
		bulletinMeteoExt.setTemperature(TEMP);
		bulletinMeteoExt.setPression(PRESSION);
		bulletinMeteoExt.setHumidite(HUMIDITE);

		return bulletinMeteoExt;
	}

}
