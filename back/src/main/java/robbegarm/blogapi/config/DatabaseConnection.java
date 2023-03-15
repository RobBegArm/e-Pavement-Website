package robbegarm.blogapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Component
public class DatabaseConnection {

    Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    //GET DATABASE URL, USERNAME AND PASSWORD FROM MACHINE ENVIRONMENT
    @Value("${DB_HOST}")
    private String url;

    @Value("${DB_NAME}")
    private String databaseName;

    @Value("${DB_USERNAME}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Value("${DB_PORT:#{null}}")
    private String dbPort;

    @Value("${DB_CLOUD_INSTANCE:#{null}}")
    private String cloudSqlInstance;

    @Value("${DB_SOCKET_FACTORY:#{null}}")
    private String socketFactory;

    private Boolean ssl = false;

    public Connection getConnectionToDatabase() { // Gets the connection to the database with MySQL Server
        try {
            if (url != null){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Properties connectionProperties = new Properties();

                if (cloudSqlInstance != null) connectionProperties.put("cloudSqlInstance", cloudSqlInstance);
                if (socketFactory != null)  connectionProperties.put("socketFactory", socketFactory);

                connectionProperties.put("useSSL", ssl);
                connectionProperties.put("user", username);
                connectionProperties.put("password", password);

                String connectionUrl = "jdbc:mysql://" + url + (dbPort != null ? ":" + dbPort : "") + "/" + databaseName;
                logger.info("Connection url: " + connectionUrl);

                Connection conn = DriverManager.getConnection(connectionUrl, connectionProperties);
                logger.info("Connected To Database");
                return conn;
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    } //Connects to the DB
}
