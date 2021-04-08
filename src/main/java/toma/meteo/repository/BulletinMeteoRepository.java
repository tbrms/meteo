package toma.meteo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import toma.meteo.bean.BulletinMeteoExt;

@Repository
public interface BulletinMeteoRepository  extends BaseRepository<BulletinMeteoExt>{
	
	public BulletinMeteoExt findByDate(LocalDateTime date);

	public List<BulletinMeteoExt> findAllByDateBetween(LocalDateTime dateDebut, LocalDateTime dateFin);

	public BulletinMeteoExt findFirstByOrderByDateDesc();
	
    @Query(value = "SELECT bm.id, bm.date, bm.temperature, bm.pression, bm.humidite FROM BULLETIN_METEO_EXT bm ORDER BY bm.id DESC LIMIT :n", nativeQuery=true)
	List<BulletinMeteoExt> getDerniersBulletins(@Param("n") int n);
}
