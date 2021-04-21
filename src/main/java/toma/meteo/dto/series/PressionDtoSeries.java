package toma.meteo.dto.series;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import toma.meteo.dto.donnee.PressionDto;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@Component
public class PressionDtoSeries extends AbstractDtoSeries<PressionDto> {

}
