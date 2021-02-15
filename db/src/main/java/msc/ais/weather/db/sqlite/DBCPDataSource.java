package msc.ais.weather.db.sqlite;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class DBCPDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBCPDataSource.class);
    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

    static {
        final String confFileName = "sqlite.conf";
        final String confFilePath = SystemUtils.getUserDir().getParentFile().getParent()
            + "/" + confFileName;

        try {
            final Config config = ConfigFactory
                .parseFile(new File(confFilePath))
                .resolve();
            final String dbName = config.getConfig("sqlitedb").getString("dbName");
            final String dbPath = config.getConfig("sqlitedb").getString("dbPath");

            LOGGER.info("DB Name: " + dbName);
            LOGGER.info("DB Path: " + dbPath);

            DATA_SOURCE.setUrl("jdbc:sqlite:" + new File(dbPath));
            // DATA_SOURCE.setUsername("");
            // DATA_SOURCE.setPassword("");
            DATA_SOURCE.setMinIdle(5);
            DATA_SOURCE.setMaxIdle(10);
            DATA_SOURCE.setTimeBetweenEvictionRunsMillis(100);
            DATA_SOURCE.setMaxOpenPreparedStatements(100);
        } catch (ConfigException e) {
            final String sqliteConfFileStructure = "sqlitedb {" + System.lineSeparator()
                + "\t dbName: \"the-db-name.db\"" + System.lineSeparator()
                + "\t dbPath: \"the-db-path\"" + System.lineSeparator()
                + "}";
            LOGGER.error("Unable to find sqlite.conf file in: " + confFilePath);
            LOGGER.error("Please add sqlite.conf in path with the following structure: "
                + System.lineSeparator()
                + sqliteConfFileStructure);
        }
    }

    private DBCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

}
