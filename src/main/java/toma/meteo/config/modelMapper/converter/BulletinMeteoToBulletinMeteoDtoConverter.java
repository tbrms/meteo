package toma.meteo.config.modelMapper.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.dto.BulletinMeteoDto;

public class BulletinMeteoToBulletinMeteoDtoConverter implements Converter<BulletinMeteoExt, BulletinMeteoDto>{

	@Override
	public BulletinMeteoDto convert(final MappingContext<BulletinMeteoExt, BulletinMeteoDto> context) {
		BulletinMeteoExt source = context.getSource();
		BulletinMeteoDto destination = context.getDestination();
		
		
		return null;
	}

}
