package ms.ais.weather.retriever;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/1/2021.
 */
public class RetrieverFactory {

    public static WeatherDataRetriever createWeatherDataRetriever() {
        return new OpenWeatherMapDataRetriever();
    }

}
