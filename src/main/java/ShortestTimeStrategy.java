import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTimeStrategy implements Strategy{
    @Override
    public void addClient(ArrayList<Server> servers, Client c){
        int minTime = Integer.MAX_VALUE;
        Server targetServer = null;
        for(Server s : servers){
            int serverWaitingTime = s.getWaitingTime().get();
            if(serverWaitingTime < minTime){
                targetServer = s;
                minTime = serverWaitingTime;
            }
        }
        targetServer.addClient(c);
    }
}
