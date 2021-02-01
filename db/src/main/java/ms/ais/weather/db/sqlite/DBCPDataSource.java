package ms.ais.weather.db.sqlite;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.SystemUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class DBCPDataSource {

    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:sqlite:" + SystemUtils.getUserHome()
            + "/sqlite/db/ms-ais-weather-schema.db");
//        ds.setUrl("jdbc:sqlite:" + SystemUtils.getUserHome()
//            + "/Documents/SqliteDB/ms-ais-weather-schema.db");
        // ds.setUsername("");
        // ds.setPassword("");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setTimeBetweenEvictionRunsMillis(100);
        ds.setMaxOpenPreparedStatements(100);
    }

    private DBCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
