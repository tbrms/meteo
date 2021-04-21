package toma.meteo.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BULLETIN_METEO_EXT")
public class BulletinMeteoExt {
	
	@Id
	@SequenceGenerator(name = "SQ_BULLETIN_METEO_EXT", sequenceName = "SQ_BULLETIN_METEO_EXT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BULLETIN_METEO_EXT")
	long id;
	@Column(nullable = false)
	LocalDateTime date;
	@Column(precision = 3,scale = 1,nullable = false)
	BigDecimal temperature;
	@Column(nullable = false)
	int pression;
	@Column(nullable = false)
	int humidite;
	
	@Override
	public String toString() {
		return String.format("Bulletin meteo [id=%d, Temperature='%s', Pression='%s'"
				+ ", Humidite='%s']", id, temperature, pression, humidite);
	}
}
