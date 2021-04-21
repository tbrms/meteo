package toma.meteo.dto.series;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
public abstract class AbstractDtoSeries<T>{
	
	String name;
	List<T> series;

}
