package org.example;

import org.example.dao.TicketDao;
import org.example.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {
    public static void main(String[] args) {
        deleteTicket();
    }

    private static void deleteTicket() {
        TicketDao instance = TicketDao.getInstance();
        boolean delete = instance.delete(56L);
        System.out.println(delete);
    }

    private static void saveTicket() {
        TicketDao instance = TicketDao.getInstance();
        Ticket ticket = new Ticket("1223145", "Taras", 3L, "B3", BigDecimal.TEN);
        Ticket savedTicket = instance.save(ticket);
        System.out.println(savedTicket);
    }
}
