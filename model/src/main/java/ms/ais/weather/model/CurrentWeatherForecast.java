package ms.ais.weather.model;

import ms.ais.weather.model.conditions.CurrentTemperatureConditions;
import ms.ais.weather.model.conditions.CurrentWeatherConditions;
import ms.ais.weather.model.conditions.CurrentWindConditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/1/2021.
 */
public class CurrentWeatherForecast {

    private final long timestamp;
    private final CurrentTemperatureConditions temperatureConditions;
    private final CurrentWeatherConditions weatherConditions;
    private final CurrentWindConditions windConditions;

    private CurrentWeatherForecast(Builder builder) {
        this.timestamp = builder.timestamp;
        this.temperatureConditions = builder.temperatureConditions;
        this.weatherConditions = builder.weatherConditions;
        this.windConditions = builder.windConditions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public CurrentTemperatureConditions getTemperatureConditions() {
        return temperatureConditions;
    }

    public CurrentWeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    public CurrentWindConditions getWindConditions() {
        return windConditions;
    }

    public interface CurrentWeatherForecastTimestamp {
        CurrentWeatherForecastTemperatureConditions timestamp(long timestamp);
    }

    public interface CurrentWeatherForecastTemperatureConditions {
        CurrentWeatherForecastWeatherConditions temperatureConditions(CurrentTemperatureConditions temperatureConditions);
    }

    public interface CurrentWeatherForecastWeatherConditions {
        CurrentWeatherForecastWindConditions weatherConditions(CurrentWeatherConditions weatherConditions);
    }

    public interface CurrentWeatherForecastWindConditions {
        CurrentWeatherForecastBuild windConditions(CurrentWindConditions windConditions);
    }

    public interface CurrentWeatherForecastBuild {
        CurrentWeatherForecast build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements
        CurrentWeatherForecastTimestamp,
        CurrentWeatherForecastTemperatureConditions,
        CurrentWeatherForecastWeatherConditions,
        CurrentWeatherForecastWindConditions,
        CurrentWeatherForecastBuild {

        private long timestamp;
        private CurrentTemperatureConditions temperatureConditions;
        private CurrentWeatherConditions weatherConditions;
        private CurrentWindConditions windConditions;

        @Override
        public CurrentWeatherForecastTemperatureConditions timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public CurrentWeatherForecastWeatherConditions temperatureConditions(CurrentTemperatureConditions temperatureConditions) {
            this.temperatureConditions = temperatureConditions;
            return this;
        }

        @Override
        public CurrentWeatherForecastWindConditions weatherConditions(CurrentWeatherConditions weatherConditions) {
            this.weatherConditions = weatherConditions;
            return this;
        }

        @Override
        public CurrentWeatherForecastBuild windConditions(CurrentWindConditions windConditions) {
            this.windConditions = windConditions;
            return this;
        }

        @Override
        public CurrentWeatherForecast build() {
            return new CurrentWeatherForecast(this);
        }
    }

    @Override
    public String toString() {
        return "CurrentWeatherForecast{" +
            "timestamp=" + timestamp +
            ", temperatureConditions=" + temperatureConditions +
            ", weatherConditions=" + weatherConditions +
            ", windConditions=" + windConditions +
            '}';
    }
}
