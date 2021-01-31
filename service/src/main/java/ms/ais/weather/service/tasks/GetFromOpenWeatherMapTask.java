package ms.ais.weather.service.tasks;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class GetFromOpenWeatherMapTask implements Callable<String> {

    private static final Logger LOGGER = Logger.getLogger(GetFromOpenWeatherMapTask.class.getName());

    private final URI uri;

    private GetFromOpenWeatherMapTask(URI uri) {
        this.uri = uri;
    }

    public static GetFromOpenWeatherMapTask createWithURI(URI uri) {
        return new GetFromOpenWeatherMapTask(uri);
    }

    public URI getURI() {
        return uri;
    }

    @Override
    public String call() throws IOException, InterruptedException {

        LOGGER.log(Level.INFO, "GET task " + uri.toString() + " START");
        long startTime = System.currentTimeMillis();

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .header("Accept", "application/json")
            .GET()
            .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
            .build()
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        long endTime = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "GET task " + uri.toString()
            + " FINISH " + (endTime - startTime)
            + " ms with status code " + httpResponse.statusCode());

        return httpResponse.body();
    }

}
