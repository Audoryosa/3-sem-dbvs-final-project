package com.auku.agentura.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlUtils {
    private static Logger logger = LoggerFactory.getLogger(PostgreSqlUtils.class);

    public static void loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException cnfe) {
            logger.error("Couldn't find driver class!");
            cnfe.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection postGresConn = null;
        try {
            postGresConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "username", "password");
        }
        catch (SQLException sqle) {
            logger.error("Couldn't connect to database!");
            sqle.printStackTrace();
            return null;
        }
        logger.info("Successfully connected to Postgres Database");

        return postGresConn;
    }
}
