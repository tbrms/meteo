package toma.meteo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import toma.meteo.bean.BulletinMeteo;
import toma.meteo.bean.ReleveMeteo;
import toma.meteo.service.BulletinMeteoService;

@SpringBootTest
public class ArduinoTest {

    private static final String HTTP = "http://";

    @Autowired
    BulletinMeteoService releveMeteoService;

    @Autowired
    Environment environnement;
    
    private static Logger logger = LogManager.getLogger(ArduinoTest.class);

    /**
     * Test de recuperation d'un releve meteo depuis l'arduino
     * @throws Exception
     */
    @Test
    public void getArduino() throws Exception {
        final String HOST = environnement.getProperty("arduino.host");
        final String PORT = environnement.getProperty("arduino.port");

        final String uri = HTTP + HOST + ":" + PORT + "/";
        logger.debug("URL de connexion a l'arduino: " + uri);

        RestTemplate restTemplate = new RestTemplate();
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        BulletinMeteo releveMeteo = restTemplate.getForObject(uri, BulletinMeteo.class);
        
        logger.debug(releveMeteo.toString());

        assertNotNull(releveMeteo,"Releve null");
        assertNotNull(releveMeteo.getTemperature(),"Temperature null");
        assertNotNull(releveMeteo.getHumidite(),"Humidite null");
        assertNotNull(releveMeteo.getPression(),"Pression null");
    }
}
