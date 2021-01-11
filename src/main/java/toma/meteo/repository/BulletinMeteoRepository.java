package toma.meteo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toma.meteo.bean.BulletinMeteo;

@Repository
public interface BulletinMeteoRepository  extends BaseRepository<BulletinMeteo>{
	
	public BulletinMeteo findByDate(Date date);

	public List<BulletinMeteo> findAllByDateBetween(Date dateDebut, Date dateFin);

	public BulletinMeteo findFirstByOrderByDateDesc();
}
