package toma.meteo.config.modelMapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteo;
import toma.meteo.dto.BulletinMeteoDto;

public class BulletinMeteoToBulletinMeteoDtoConverter implements Converter<BulletinMeteo, BulletinMeteoDto>{

	@Override
	public BulletinMeteoDto convert(final MappingContext<BulletinMeteo, BulletinMeteoDto> context) {
		BulletinMeteo source = context.getSource();
		BulletinMeteoDto destination = context.getDestination();
		
		
		return null;
	}

}
