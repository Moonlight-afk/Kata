package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Util {
    private static Connection connection = null;
    public static Connection getConnection() {
        try {
            if (null == connection || connection.isClosed()) {
                Properties props = getProps();
                connection = DriverManager
                        .getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Objects.requireNonNull(Util.class.getResource("/database.properties")).toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
    }
}
