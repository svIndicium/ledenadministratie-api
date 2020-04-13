package hu.indicium.dev.ledenadministratie.auth.responses;

public class RequestPasswordChangeTicketResponse {
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
