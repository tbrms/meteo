package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import toma.meteo.bean.BulletinMeteo;
import toma.meteo.service.BulletinMeteoService;

@SpringBootTest
//@ActiveProfiles("profiltoma,prod")
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
		Date date = new Date();

		BulletinMeteo bulletinMeteo = getBulletinMeteo(date);
		BulletinMeteo bulletinMeteoRetour = new BulletinMeteo();

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
		LocalDateTime localDate = LocalDateTime.of(2000,01,01,00,05);
		LocalDateTime localDateDebut = LocalDateTime.of(2000,01,01,00,00);
		LocalDateTime localDateFin = LocalDateTime.of(2000,01,01,00,10);

		Date date = Date.from(localDateDebut.atZone(ZoneId.systemDefault()).toInstant());
		Date dateDebut = Date.from(localDateDebut.atZone(ZoneId.systemDefault()).toInstant());
		Date dateFin = Date.from(localDateFin.atZone(ZoneId.systemDefault()).toInstant());

		BulletinMeteo bulletinMeteo = getBulletinMeteo(date);
		bulletinMeteo.setDate(date);

		bulletinMeteoService.ajouter(bulletinMeteo);

		List<BulletinMeteo> listeBulletin = bulletinMeteoService.lireEntreDate(dateDebut, dateFin);

		assertNotNull(listeBulletin, "La liste est null");
		assertEquals(1,listeBulletin.size(), "La taille de la liste est differente de 1");
		assertEquals(date, listeBulletin.get(0).getDate(), "La date n'est pas correcte");

		bulletinMeteoService.effacer(bulletinMeteo);
	}

	/**
	 * Creation d'un bulletin meteo
	 * @return le bulletin meteo cree
	 */
	private BulletinMeteo getBulletinMeteo(Date date) {
		BulletinMeteo bulletinMeteo = new BulletinMeteo();

		bulletinMeteo.setDate(date);
		bulletinMeteo.setTemperature(TEMP);
		bulletinMeteo.setPression(PRESSION);
		bulletinMeteo.setHumidite(HUMIDITE);

		return bulletinMeteo;
	}

}
