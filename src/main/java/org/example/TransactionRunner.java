package org.example;

import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        long flightId = 8;

        var deleteFlightSql = "delete from practice.flight where id = " + flightId;
        var deleteTicketsSql = "delete from practice.ticket where flight_id = " + flightId;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);
            int[] statements = statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            if(connection != null){
                connection.rollback();
            }
            throw e;
        } finally {
            if(connection != null){
                connection.close();
            }
            if(statement != null){
                statement.close();
            }
        }
    }
}
