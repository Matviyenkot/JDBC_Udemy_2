package org.example;

import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        var deleteFlightSql = """
                delete from practice.flight where id = ?;
                """;
        var deleteTicketsSql = """
                delete from practice.ticket where flight_id = ?;
                """;
        long flightId = 9;
        Connection connection = null;
        PreparedStatement deleteFlightStm = null;
        PreparedStatement deleteTicketsStatement = null;

        try {
            connection = ConnectionManager.open();
            deleteFlightStm = connection.prepareStatement(deleteFlightSql);
            deleteTicketsStatement = connection.prepareStatement(deleteTicketsSql);

            connection.setAutoCommit(false);

            deleteFlightStm.setLong(1, flightId);
            deleteTicketsStatement.setLong(1, flightId);

            deleteTicketsStatement.executeUpdate();
            if(true){
                throw new RuntimeException("OOOOps");
            }
            deleteFlightStm.executeUpdate();
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
            if(deleteFlightStm != null){
                deleteFlightStm.close();
            }
            if(deleteTicketsStatement != null){
                deleteTicketsStatement.close();
            }
        }
    }
}
