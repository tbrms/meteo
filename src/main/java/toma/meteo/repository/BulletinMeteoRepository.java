package toma.meteo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import toma.meteo.bean.BulletinMeteo;

@Repository
public interface BulletinMeteoRepository  extends BaseRepository<BulletinMeteo>{
	
	public BulletinMeteo findByDate(Date date);

	public List<BulletinMeteo> findAllByDateBetween(Date dateDebut, Date dateFin);

	public BulletinMeteo findFirstByOrderByDateDesc();
	
    @Query(value = "SELECT bm.id, bm.date, bm.temperature, bm.pression, bm.humidite FROM Bulletin_Meteo bm ORDER BY bm.id DESC LIMIT :n", nativeQuery=true)
	List<BulletinMeteo> getDerniersBulletins(@Param("n") int n);
}
