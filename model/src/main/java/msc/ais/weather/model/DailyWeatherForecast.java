package msc.ais.weather.model;

import msc.ais.weather.model.conditions.DailyTemperatureConditions;
import msc.ais.weather.model.conditions.DailyWeatherConditions;
import msc.ais.weather.model.conditions.DailyWindConditions;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 10/1/2021.
 * @author Steve Labrinos [stalab at linuxmail.org] on 13/2/2021.
 */
public class DailyWeatherForecast {

    private final long timestamp;
    private final DailyTemperatureConditions temperatureConditions;
    private final DailyWeatherConditions weatherConditions;
    private final DailyWindConditions windConditions;

    private DailyWeatherForecast(Builder builder) {
        this.timestamp = builder.timestamp;
        this.temperatureConditions = builder.temperatureConditions;
        this.weatherConditions = builder.weatherConditions;
        this.windConditions = builder.windConditions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public DailyTemperatureConditions getDailyTemperatureConditions() {
        return temperatureConditions;
    }

    public DailyWeatherConditions getDailyWeatherConditions() {
        return weatherConditions;
    }

    public DailyWindConditions getDailyWindConditions() {
        return windConditions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public interface DailyWeatherForecastTimestamp {
        DailyWeatherForecastTemperatureConditions timestamp(long timestamp);
    }

    public interface DailyWeatherForecastTemperatureConditions {
        DailyWeatherForecastWeatherConditions temperatureConditions(DailyTemperatureConditions temperatureConditions);
    }

    public interface DailyWeatherForecastWeatherConditions {
        DailyWeatherForecastWindConditions weatherConditions(DailyWeatherConditions weatherConditions);
    }

    public interface DailyWeatherForecastWindConditions {
        DailyWeatherForecastBuild windConditions(DailyWindConditions windConditions);
    }

    public interface DailyWeatherForecastBuild {
        DailyWeatherForecast build();
    }

    public static class Builder implements
        DailyWeatherForecastTimestamp,
        DailyWeatherForecastTemperatureConditions,
        DailyWeatherForecastWeatherConditions,
        DailyWeatherForecastWindConditions,
        DailyWeatherForecastBuild {

        private long timestamp;
        private DailyTemperatureConditions temperatureConditions;
        private DailyWeatherConditions weatherConditions;
        private DailyWindConditions windConditions;

        @Override
        public DailyWeatherForecastTemperatureConditions timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public DailyWeatherForecastWeatherConditions temperatureConditions(DailyTemperatureConditions temperatureConditions) {
            this.temperatureConditions = temperatureConditions;
            return this;
        }

        @Override
        public DailyWeatherForecastWindConditions weatherConditions(DailyWeatherConditions weatherConditions) {
            this.weatherConditions = weatherConditions;
            return this;
        }

        @Override
        public DailyWeatherForecastBuild windConditions(DailyWindConditions windConditions) {
            this.windConditions = windConditions;
            return this;
        }

        @Override
        public DailyWeatherForecast build() {
            return new DailyWeatherForecast(this);
        }
    }

    @Override
    public String toString() {
        return "DailyWeatherForecast{" +
            "timestamp=" + timestamp +
            ", temperatureConditions=" + temperatureConditions +
            ", weatherConditions=" + weatherConditions +
            ", windConditions=" + windConditions +
            '}';
    }
}
