package org.example;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDBCRunner
{
    public static void main( String[] args ) {
//        List<Long> ticketsByFlightId = getTicketsByFlightId(2);
//        System.out.println(ticketsByFlightId);
        try {
            System.out.println(getFlightsBetween(LocalDate.of(2020, 07, 01).atStartOfDay(), LocalDateTime.now()));
        }
        finally {
            ConnectionManager.closePool();
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end){
        String sql = """
                select id
                from practice.flight
                where departure_date between ? and ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setTimestamp(1, Timestamp.valueOf(start));
            prepareStatement.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()){
                result.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<Long> getTicketsByFlightId(long flightId){
        String sql = """
                select id from flight_repository.practice.ticket
                where flight_id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
        var prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setLong(1, flightId);
            ResultSet resultSet = prepareStatement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
