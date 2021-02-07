package ms.ais.weather.api.enums;

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
public enum ServerConfig {
    INSTANCE("server.conf");

    private final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);
    private final Config CONFIG;

    ServerConfig(String confFileName) {
        final String confFilePath = SystemUtils.getUserDir().getParentFile().getParent()
            + "/" + confFileName;

        try {
            CONFIG = ConfigFactory
                .parseFile(new File(confFilePath))
                .resolve();
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

    public int getPort() {
        return getServerConfig().getInt("port");
    }

    private Config getServerConfig() {
        return CONFIG.getConfig("server");
    }
}
