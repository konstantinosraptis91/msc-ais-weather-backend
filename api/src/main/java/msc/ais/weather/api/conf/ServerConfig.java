package msc.ais.weather.api.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020.
 */
public class ServerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);
    private static final Config SERVER_CONFIG;

    static {
        final String confFileName = "server.conf";
        final String confFilePath = SystemUtils.getUserDir().getParentFile().getParent()
            + "/" + confFileName;

        try {
            Config config = ConfigFactory
                .parseFile(new File(confFilePath))
                .resolve();
            LOGGER.info("Server config initialized: " + confFilePath);
            SERVER_CONFIG = config.getConfig("server");
        } catch (ConfigException e) {
            final String sqliteConfFileStructure = "server {" + System.lineSeparator()
                + "\t port: the-port" + System.lineSeparator()
                + "}";
            LOGGER.error("Unable to find server.conf file in: " + confFilePath);
            LOGGER.error("Please add server.conf in path with the following structure: "
                + System.lineSeparator()
                + sqliteConfFileStructure);
            throw new IllegalStateException();
        }
    }

    private ServerConfig() {
    }

    public static int getPort() {
        return SERVER_CONFIG.getInt("port");
    }

}
