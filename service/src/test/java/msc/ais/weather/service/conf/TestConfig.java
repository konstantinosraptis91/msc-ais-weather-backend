package msc.ais.weather.service.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/2/2021.
 */
public class TestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfig.class);

    private static final Config TEST_CONFIG;

    static {
        final String confFileName = "test.conf";
        final String confFilePath = SystemUtils.getUserDir().getParentFile().getParent()
            + "/" + confFileName;

        try {
            Config config = ConfigFactory
                .parseFile(new File(confFilePath))
                .resolve();
            TEST_CONFIG = config.getConfig("test");
        } catch (ConfigException e) {
            final String sqliteConfFileStructure = "test {" + System.lineSeparator()
                + "\t openWeatherMapAPIKey: \"the-key\"" + System.lineSeparator()
                + "\t ipStackAPIKey: \"the-port\"" + System.lineSeparator()
                + "}";
            LOGGER.error("Unable to find " + confFileName + " file in: " + confFilePath);
            LOGGER.error("Please add server.conf in path with the following structure: "
                + System.lineSeparator()
                + sqliteConfFileStructure);
            throw new IllegalStateException();
        }
    }

    private TestConfig() {

    }

    public static String getOpenWeatherMapAPIKey() {
        return TEST_CONFIG.getString("openWeatherMapAPIKey");
    }

    public static String getIPStackAPIKey() {
        return TEST_CONFIG.getString("ipStackAPIKey");
    }

}
