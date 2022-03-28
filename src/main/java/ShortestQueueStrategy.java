import java.util.ArrayList;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addClient(ArrayList<Server> servers, Client c) {
        int minQueueSize = Integer.MAX_VALUE;
        Server targetServer = null;
        for(Server s : servers){
            int serverQueueSize = s.getQueue().size();
            if(serverQueueSize < minQueueSize){
                targetServer = s;
                minQueueSize = serverQueueSize;
            }
        }
        targetServer.addClient(c);
    }
}
