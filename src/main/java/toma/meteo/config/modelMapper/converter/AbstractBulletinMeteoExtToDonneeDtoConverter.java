package toma.meteo.config.modelMapper.converter;

import java.math.BigDecimal;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.dto.donnee.DonneeDto;
import toma.meteo.dto.donnee.HumiditeDto;
import toma.meteo.dto.donnee.PressionDto;
import toma.meteo.dto.donnee.TemperatureDto;
import toma.meteo.utils.DateUtils;

public abstract class AbstractBulletinMeteoExtToDonneeDtoConverter<T extends DonneeDto> implements Converter<BulletinMeteoExt, T>{

	/**
	 * Convertir BulletinMeteoExt en <T>
	 */
	@Override
	public T convert(final MappingContext<BulletinMeteoExt, T> context) {
		BulletinMeteoExt source = context.getSource();
		T destination = context.getDestination();
		
		destination.setName(source.getDate().format(DateUtils.FORMATTER_COURT));
		if(destination instanceof TemperatureDto) {
			destination.setValue(source.getTemperature());
		} else if(destination instanceof PressionDto){
			destination.setValue(
					new BigDecimal(String.valueOf(source.getPression())));
		} else if(destination instanceof HumiditeDto){
			destination.setValue(
					new BigDecimal(String.valueOf(source.getHumidite())));
		}
		
		return destination;
	}

}
