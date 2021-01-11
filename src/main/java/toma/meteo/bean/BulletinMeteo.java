package toma.meteo.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.googlecode.jmapper.annotations.JMap;

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
	@JMap
	private float temperature;
	@JMap
	private float pression;
	@JMap
	private float humidite;

}
