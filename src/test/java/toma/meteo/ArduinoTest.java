package toma.meteo;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import toma.meteo.bean.ReleveMeteo;
import toma.meteo.service.BulletinMeteoService;
import toma.meteo.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class ArduinoTest {

    private static final String HTTP = "http://";

    @Autowired
    BulletinMeteoService releveMeteoService;

    @Autowired
    Environment environnement;

    /**
     * Test de recuperation d'un releve meteo depuis l'arduino
     * @throws Exception
     */
    @Test
    public void getArduino() throws Exception {
        final String HOST = environnement.getProperty("arduino.host");
        final String PORT = environnement.getProperty("arduino.port");

        final String uri = HTTP + HOST + ":" + PORT + "/";

        Date date = new Date();
        RestTemplate restTemplate = new RestTemplate();

        ReleveMeteo releveMeteo = restTemplate.getForObject(uri, ReleveMeteo.class);

        assertNotNull(releveMeteo,"Releve null");
        assertNotNull(releveMeteo.getTemperature(),"Temperature null");
        assertNotNull(releveMeteo.getHumidite(),"Humidite null");
        assertNotNull(releveMeteo.getPression(),"Pression null");
    }
}
