package org.example.dao;


public class TicketDao {
    private static final TicketDao INSTANCE = new TicketDao();
    private TicketDao(){};

    public TicketDao getInstance(){
        return INSTANCE;
    }


}
