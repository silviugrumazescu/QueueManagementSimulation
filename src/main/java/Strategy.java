import java.util.ArrayList;

public interface Strategy {
    public void addClient(ArrayList<Server> servers, Client c);
}