package toma.meteo.bean;

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
public class ReleveMeteo {

	private float temperature;
	private float pression;
	private float humidite;
	
	public String toString() {
		return "Releve meteo: Temperature=" + this.temperature + ", Pression=" + this.pression
				+ ", Humidite=" + this.humidite;
	}

}
