import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Client> queue;
    private AtomicInteger waitingTime;
    private int totalWaitingTime = 0;
    private int totalServiceTime = 0;
    private int totalNumberOfClients = 0;
    private int serverID;
    public int expectedFinishTime;
    public boolean processingClient = false;
    public int currentlyProcessedID = -1;


    public Server(int serverID)
    {
        this.serverID = serverID;
        queue = new LinkedBlockingQueue<Client>();
        waitingTime = new AtomicInteger(0);

        totalWaitingTime = 0;
        totalNumberOfClients = 0;
        expectedFinishTime = 0;
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                Client c = queue.take();
                processingClient = true;
                currentlyProcessedID = c.getId();
                expectedFinishTime = SimulationManager.currentTime + c.getServiceTime();
                for(int i = 0; i < c.getServiceTime(); i++){
                    Thread.sleep(1000);
                    waitingTime.getAndDecrement();
                }


                // ne resincronizam cu timpul din simulationManager
                while(SimulationManager.currentTime != expectedFinishTime) {
                    Thread.sleep(100);
                }

                processingClient = false;
                currentlyProcessedID = -1;
            }
        }
        catch(InterruptedException ex){
            System.out.println("Serverul " + this.serverID + " s-a oprit");
        }
    }
    public void addClient(Client c){
        try {
            System.out.println("trying to add client to server " + this.serverID);
            queue.put(c);
            waitingTime.set(waitingTime.get() + c.getServiceTime());
            totalNumberOfClients++;
            totalServiceTime += c.getServiceTime();
            totalWaitingTime += processWaitingTimeForLastAddedClient();
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private int processWaitingTimeForLastAddedClient(){
        int lastClientWaitingTime = 0;
        for(Client i: queue){
            lastClientWaitingTime += i.getServiceTime();
        }
        if(processingClient == true){
            lastClientWaitingTime += expectedFinishTime - SimulationManager.currentTime;
        }
        return lastClientWaitingTime;
    }

    public int getServerID(){
        return serverID;
    }
    public BlockingQueue<Client> getQueue(){
        return this.queue;
    }
    public AtomicInteger getWaitingTime(){
        return this.waitingTime;
    }
    public int getTotalWaitingTime(){
        return totalWaitingTime;
    }
    public int getTotalServiceTime() { return totalServiceTime; }
    public int getTotalNumberOfClients(){
        return totalNumberOfClients;
    }
}
