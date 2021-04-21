package toma.meteo.config.modelMapper.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.dto.donnee.TemperatureDto;
import toma.meteo.dto.series.TemperatureDtoSeries;

public class BulletinMeteoExtToTemperatureDtoSeriesConverter 
implements Converter<List<BulletinMeteoExt>, TemperatureDtoSeries> {
	
	@Autowired
	ModelMapper mapper;
	
	/**
	 * Convertir un BulletinMeteoExt en TemperatureDtoSeries
	 */
	@Override
	public TemperatureDtoSeries convert(MappingContext<List<BulletinMeteoExt>, TemperatureDtoSeries> context) {
		List<BulletinMeteoExt> source = context.getSource();
		TemperatureDtoSeries destination = new TemperatureDtoSeries();
		
		List<TemperatureDto> listeTemperatureDto = new ArrayList<TemperatureDto>();
		mapper.map(source, listeTemperatureDto);
		
		destination.setName("Temperature");
		destination.setSeries(listeTemperatureDto);
		
		return destination;
	}

}
