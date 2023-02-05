package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        if (sessionFactory == null) {
            try {
                properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
                properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "root");
                properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                properties.setProperty("show_sql", "true");
                properties.setProperty("hibernate.current_session_context_class", "thread");

                properties.setProperty("hibernate.hbm2ddl.auto", "");

                sessionFactory = new Configuration().addProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
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
