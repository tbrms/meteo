package toma.meteo.config.modelMapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.dto.TemperatureDto;
import toma.meteo.utils.DateUtils;

public class BulletinMeteoExtToTemperatureDtoConverter implements Converter<BulletinMeteoExt, TemperatureDto>{

	/**
	 * Convertir BulletinMeteoExt en TemperatureDto
	 */
	@Override
	public TemperatureDto convert(final MappingContext<BulletinMeteoExt, TemperatureDto> context) {
		BulletinMeteoExt source = context.getSource();
		TemperatureDto destination = context.getDestination();
		
		destination.setName(source.getDate().format(DateUtils.FORMATTER_COURT));
		destination.setValue(source.getTemperature());
		
		return destination;
	}

}
