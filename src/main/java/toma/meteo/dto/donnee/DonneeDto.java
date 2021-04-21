package toma.meteo.dto.donnee;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
public abstract class DonneeDto {
	
	private String name;
	private BigDecimal value;

}
