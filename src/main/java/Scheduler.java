import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> serverList;
    private ArrayList<Thread> threadList;
    private int maxNoServers;
    private Strategy strategy;

    public Scheduler(int maxNoServers){
        serverList = new ArrayList<Server>();
        threadList = new ArrayList<Thread>();
        for(int i = 1; i <= maxNoServers; i++)
        {
            Server s = new Server(i);
            serverList.add(s);
            Thread t = new Thread(s);
            threadList.add(t);
            t.start();
        }
    }
    public void dispatchClient(Client c){
        strategy.addClient(serverList, c);
    }

    public void changeStrategy(SelectionPolicy selectionPolicy){
        switch(selectionPolicy){
            case SHORTEST_QUEUE:
                this.strategy = new ShortestQueueStrategy();
                break;
            case SHORTEST_TIME:
                this.strategy = new ShortestTimeStrategy();
                break;
            default:
                this.strategy = new ShortestTimeStrategy();
        }
    }

    public ArrayList<Server> getServerList(){
        return serverList;
    }
    public ArrayList<Thread> getThreadList(){
        return threadList;
    }

}
