package toma.meteo.config.modelMapper.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.bean.ReleveMeteo;

public class ReleveMeteoToBulletinMeteoConverter implements Converter<ReleveMeteo, BulletinMeteoExt> {
	
	@Override
	public BulletinMeteoExt convert(MappingContext<ReleveMeteo, BulletinMeteoExt> context) {
		ReleveMeteo source = context.getSource();
		BulletinMeteoExt destination = new BulletinMeteoExt();
		
		destination.setTemperature(new BigDecimal(String.valueOf(source.getTemperature()))
				.setScale(1, RoundingMode.HALF_UP));
		destination.setPression(Math.round(source.getPression()));
		destination.setHumidite(Math.round(source.getHumidite()));
		
		return destination;
	}

}
