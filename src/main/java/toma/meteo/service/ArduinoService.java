package toma.meteo.service;

import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import toma.meteo.bean.BulletinMeteo;
import toma.meteo.bean.ReleveMeteo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@ActiveProfiles({"dev","prod"})
@Service
public class ArduinoService {

    private static Logger logger = LoggerFactory.getLogger(ArduinoService.class);

    private static final String HTTP = "http://";

    @Autowired
    private Environment environment;

    public Optional<BulletinMeteo> getBulletinMeteo(){

        RestTemplate restTemplate = new RestTemplate();
        BulletinMeteo bulletinMeteo = null;

        try {
            Properties properties = ConfigService.getConfig();
            //final String HOST = properties.getProperty("arduino.host");
            //final String PORT = properties.getProperty("arduino.port");

            final String HOST = environment.getProperty("arduino.host");
            final String PORT = environment.getProperty("arduino.port");

            final String uri = HTTP + HOST + ":" + PORT + "/";

            ReleveMeteo releveMeteo = restTemplate.getForObject(uri, ReleveMeteo.class);

            if(releveMeteo != null){
                logger.debug(releveMeteo.toString());

                JMapper<BulletinMeteo,ReleveMeteo> mapper =
                        new JMapper<BulletinMeteo, ReleveMeteo>(BulletinMeteo.class, ReleveMeteo.class);

                bulletinMeteo = mapper.getDestination(releveMeteo);

                if(bulletinMeteo != null){
                    bulletinMeteo.setDate(new Date());
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
