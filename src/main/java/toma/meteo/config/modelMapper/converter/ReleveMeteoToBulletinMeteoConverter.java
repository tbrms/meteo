package toma.meteo.config.modelMapper.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteo;
import toma.meteo.bean.ReleveMeteo;

public class ReleveMeteoToBulletinMeteoConverter implements Converter<ReleveMeteo, BulletinMeteo> {
	
	@Override
	public BulletinMeteo convert(MappingContext<ReleveMeteo, BulletinMeteo> context) {
		ReleveMeteo source = context.getSource();
		BulletinMeteo destination = new BulletinMeteo();
		
		destination.setTemperature(new BigDecimal(String.valueOf(source.getTemperature()))
				.setScale(1, RoundingMode.HALF_UP));
		destination.setPression(Math.round(source.getPression()));
		destination.setHumidite(Math.round(source.getHumidite()));
		
		return destination;
	}

}
