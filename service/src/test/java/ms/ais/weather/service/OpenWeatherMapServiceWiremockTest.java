package ms.ais.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import ms.ais.weather.model.response.CurrentWeatherForecastResponse;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.util.NoSuchElementException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/2/21.
 */
public class OpenWeatherMapServiceWiremockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options()
        .port(9000)
        .bindAddress("localhost"));

    @Test
    public void testGetCurrentWeatherForecastForCurrentLocation() throws Exception {

        InputStream inputStream1 =
            OpenWeatherMapServiceWiremockTest.class.getResourceAsStream(
                "/mock/ip_stack_api/ipStackResponse.json");

        InputStream inputStream2 =
            OpenWeatherMapServiceWiremockTest.class.getResourceAsStream(
                "/mock/open_weather_map_api/currentOpenWeatherMapResponse.json");

        final String ipStackURL = "/check?access_key=8f0513df0cc0f66506cad2a187e485d6";
        doTheStubbing(inputStream1, ipStackURL, 1);

        final String openWeatherMapURL = "/weather?q=Gerakas&appid=200681ee8b9be15aafc017130d88cd41&units=metric";
        doTheStubbing(inputStream2, openWeatherMapURL, 2);

        CurrentWeatherForecastResponse response = ServiceFactory.createWeatherService()
            .getCurrentWeatherForecastResponse()
            .orElseThrow(() -> new NoSuchElementException("Error... empty Response"));

        System.out.println(response.getCity());
        System.out.println(response.getForecast());
    }

    private void doTheStubbing(InputStream inputStream, String url) throws Exception {
        doTheStubbing(inputStream, url, 1);
    }

    private void doTheStubbing(InputStream inputStream, String url, int priority) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Object jsonObject = mapper.readValue(inputStream, Object.class);
        String jsonResponse = mapper.writeValueAsString(jsonObject);

        stubFor(
            get(urlEqualTo(url))
                .atPriority(priority)
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));
    }

}
