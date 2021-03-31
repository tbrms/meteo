package toma.meteo.dto;

import java.math.BigDecimal;
import java.util.Date;

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
@Component
public class BulletinMeteoDto {

	private long id;
	private Date date;
	private BigDecimal temperature;
	private int pression;
	private int humidite;
}
