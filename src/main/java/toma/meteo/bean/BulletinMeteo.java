package toma.meteo.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

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
@Component
public class BulletinMeteo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Date date;
	@Column(precision = 3,scale = 1)
	private BigDecimal temperature;
	private int pression;
	private int humidite;
	
	@Override
	public String toString() {
		return String.format("Buletin meteo[id=%d, Temperature='%s', Pression='%s'"
				+ ", Humidite='%s']", id, temperature, pression, humidite);
	}
}
