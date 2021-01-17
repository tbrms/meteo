package toma.meteo.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;

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
	private BigDecimal temperature;
	@JMap
	private int pression;
	@JMap
	private int humidite;
	
	@JMapConversion(from={"pression"}, to={"pression"})
    public int conversionPression(float pression){
		return (int)pression;
    }
	
	@JMapConversion(from={"humidite"}, to={"humidite"})
    public int conversionHumidite(float humidite){
		return (int)humidite;
    }
	
	@JMapConversion(from={"temperature"}, to={"temperature"})
    public BigDecimal conversionTemperature(BigDecimal temperature){
		//BigDecimal temperatureBigDecimal = BigDecimal.valueOf(temperature);
		return temperature.setScale(2, RoundingMode.HALF_UP);
    }
}
