package discord.javaind30;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:src/main/resources/db/db_bot.db";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static DbHandler instance = null;

    private final Connection connection;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public void createTables() {
        try {
            Statement statement=this.connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS tests (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT," +
                    "picture BLOB)");
            statement.execute("CREATE TABLE IF NOT EXISTS questions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idTest INTEGER," +
                    "question TEXT," +
                    "picture BLOB," +
                    "answers TEXT," +
                    "explanation TEXT)");
            statement.close();
        } catch (SQLException e) {
            LOGGER.error("Error creating table", e);

        }
    }
}