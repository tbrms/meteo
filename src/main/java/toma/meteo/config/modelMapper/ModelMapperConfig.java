package toma.meteo.config.modelMapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import toma.meteo.config.modelMapper.converter.ReleveMeteoToBulletinMeteoExtConverter;

/**
 * La classe de configuration de Model Mapper
 */
@Configuration
public class ModelMapperConfig {

  /**
   * Model mapper.
   * @return the model mapper
   */
  @Bean
  public ModelMapper modelMapper() {

    final ModelMapper modelMapper = new ModelMapper();

    // un converter de BulletinMeteo et BulletinMeteoDto
    modelMapper.addConverter(new ReleveMeteoToBulletinMeteoExtConverter());

    modelMapper.getConfiguration()

        // En plus de la config de base de modelMapper
        // activation du matching sur les champs privés
        .setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE);

    return modelMapper;
  }

}
