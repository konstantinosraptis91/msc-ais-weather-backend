package msc.ais.weather.db.sqlite;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class DBCPDataSourceTest {

    @Disabled
    @Test
    public void testGetConnection() throws Exception {

        String query = "SELECT * FROM user";

        Statement statement = DBCPDataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        System.out.println(resultSet.getString(2));
    }

}
