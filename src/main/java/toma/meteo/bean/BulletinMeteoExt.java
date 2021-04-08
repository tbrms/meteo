package toma.meteo.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//@Component
@Table(name = "BULLETIN_METEO_EXT")
public class BulletinMeteoExt {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	LocalDateTime date;
	@Column(precision = 3,scale = 1)
	BigDecimal temperature;
	int pression;
	int humidite;
	
	
	@Override
	public String toString() {
		return String.format("Bulletin meteo[id=%d, Temperature='%s', Pression='%s'"
				+ ", Humidite='%s']", id, temperature, pression, humidite);
	}
}
