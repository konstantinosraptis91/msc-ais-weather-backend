package msc.ais.weather.service;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/2/2021.
 */
public class ServiceOptions {

    private final String openWeatherMapAPIKey;
    private final String ipStackAPIKey;

    private ServiceOptions(Builder builder) {
        this.openWeatherMapAPIKey = builder.openWeatherMapAPIKey;
        this.ipStackAPIKey = builder.ipStackAPIKey;
    }

    public String getIpStackAPIKey() {
        return ipStackAPIKey;
    }

    public String getOpenWeatherMapAPIKey() {
        return openWeatherMapAPIKey;
    }

    public interface ServiceOptionsOpenWeatherMapAPIKey {
        ServiceOptionsIPStackAPIKey openWeatherMapAPIKey(String key);
    }

    public interface ServiceOptionsIPStackAPIKey {
        ServiceOptionsBuild ipStackAPIKey(String key);
    }

    public interface ServiceOptionsBuild {
        ServiceOptions build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
        implements
        ServiceOptionsOpenWeatherMapAPIKey,
        ServiceOptionsIPStackAPIKey,
        ServiceOptionsBuild {

        private String openWeatherMapAPIKey;
        private String ipStackAPIKey;

        @Override
        public ServiceOptionsIPStackAPIKey openWeatherMapAPIKey(String key) {
            this.openWeatherMapAPIKey = key;
            return this;
        }

        @Override
        public ServiceOptionsBuild ipStackAPIKey(String key) {
            this.ipStackAPIKey = key;
            return this;
        }

        @Override
        public ServiceOptions build() {
            return new ServiceOptions(this);
        }
    }

}
