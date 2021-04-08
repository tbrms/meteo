package toma.meteo.service;


import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import toma.meteo.bean.BulletinMeteoExt;
import toma.meteo.bean.ReleveMeteo;

//@ActiveProfiles({"prod","profiltoma"})
@Service
public class ArduinoService {

    private static Logger logger = LogManager.getLogger(ArduinoService.class);

    private static final String HTTP = "http://";
    
    @Autowired
	ModelMapper mapper;

    @Autowired
    private Environment environment;

    public Optional<BulletinMeteoExt> getBulletinMeteo(){

        RestTemplate restTemplate = new RestTemplate();
        BulletinMeteoExt bulletinMeteo = null;

        try {
            //Properties properties = ConfigService.getConfig();
            //final String HOST = properties.getProperty("arduino.host");
            //final String PORT = properties.getProperty("arduino.port");

            final String HOST = environment.getProperty("arduino.host");
            final String PORT = environment.getProperty("arduino.port");

            final String uri = HTTP + HOST + ":" + PORT + "/";

            ReleveMeteo releveMeteo = restTemplate.getForObject(uri, ReleveMeteo.class);

            if(releveMeteo != null){
                logger.debug(releveMeteo.toString());

                bulletinMeteo = 
        				this.mapper.map(releveMeteo, BulletinMeteoExt.class);

                if(bulletinMeteo != null){
                    bulletinMeteo.setDate(LocalDateTime.now());
                    return Optional.of(bulletinMeteo);
                } else {
                    logger.error("Le bulletin meteo est nul. Verifier le mapper.");
                    return Optional.empty();
                }
            } else {
                logger.error("Le releve meteo est nul. Verifier l'arduino");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la releve de la meteo: " + e.getMessage());
            return Optional.empty();
        }
    }
}
