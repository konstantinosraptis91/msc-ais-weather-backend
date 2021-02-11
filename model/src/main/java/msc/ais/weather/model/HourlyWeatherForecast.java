package msc.ais.weather.model;

import msc.ais.weather.model.conditions.HourlyTemperatureConditions;
import msc.ais.weather.model.conditions.HourlyWeatherConditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 22/1/2021.
 */
public class HourlyWeatherForecast {

    private final long timestamp;
    private final HourlyTemperatureConditions temperatureConditions;
    private final HourlyWeatherConditions weatherConditions;

    private HourlyWeatherForecast(Builder builder) {
        this.timestamp = builder.timestamp;
        this.temperatureConditions = builder.temperatureConditions;
        this.weatherConditions = builder.weatherConditions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public HourlyTemperatureConditions getTemperatureConditions() {
        return temperatureConditions;
    }

    public HourlyWeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    public interface HourlyWeatherForecastTimestamp {
        HourlyWeatherForecastTemperatureConditions timestamp(long timestamp);
    }

    public interface HourlyWeatherForecastTemperatureConditions {
        HourlyWeatherForecastWeatherConditions weatherConditions(HourlyWeatherConditions weatherConditions);
    }

    public interface HourlyWeatherForecastWeatherConditions {
        HourlyWeatherForecastBuild temperatureConditions(HourlyTemperatureConditions temperatureConditions);
    }

    public interface HourlyWeatherForecastBuild {
        HourlyWeatherForecast build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements
        HourlyWeatherForecastTimestamp,
        HourlyWeatherForecastTemperatureConditions,
        HourlyWeatherForecastWeatherConditions,
        HourlyWeatherForecastBuild {

        private long timestamp;
        private HourlyTemperatureConditions temperatureConditions;
        private HourlyWeatherConditions weatherConditions;

        @Override
        public HourlyWeatherForecastTemperatureConditions timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public HourlyWeatherForecastBuild temperatureConditions(HourlyTemperatureConditions temperatureConditions) {
            this.temperatureConditions = temperatureConditions;
            return this;
        }

        @Override
        public HourlyWeatherForecastWeatherConditions weatherConditions(HourlyWeatherConditions weatherConditions) {
            this.weatherConditions = weatherConditions;
            return this;
        }

        @Override
        public HourlyWeatherForecast build() {
            return new HourlyWeatherForecast(this);
        }
    }

    @Override
    public String toString() {
        return "HourlyWeatherForecast{" +
            "timestamp=" + timestamp +
            ", temperatureConditions=" + temperatureConditions +
            ", weatherConditions=" + weatherConditions +
            '}';
    }
}
