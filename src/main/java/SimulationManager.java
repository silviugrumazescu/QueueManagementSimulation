import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable{

    private int maxSimulationTime,maxArrivalTime, minArrivalTime, maxServiceTime, minServiceTime, noClients, noServers;
    private int peakHourStart, peakHourFinish, peakHourClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    public static volatile int currentTime;
    private boolean ALL_CLIENTS_SERVED = false;
    public Logger logger;
    private Scheduler scheduler;

    private ArrayList<Client> savedGeneratedClients, generatedClients;

    public SimulationManager(Logger logger) {
        this.logger = logger;
    }
    @Override
    public void run(){
        try{
            loadGeneratedClients();

            logger.addLine("Simulation started");
            scheduler = new Scheduler(noServers);
            scheduler.changeStrategy(selectionPolicy);
            ALL_CLIENTS_SERVED = false;
            currentTime = 0;
            while(currentTime < maxSimulationTime && !ALL_CLIENTS_SERVED){
                logger.addLine("Time: " + currentTime);
                System.out.println("Time: " + currentTime);
                int idx = 0;
                while(idx < generatedClients.size()){
                    if(generatedClients.get(idx).getArrivalTime() == currentTime){
                        System.out.println("Dispatching client : " + generatedClients.get(idx).getId());
                        scheduler.dispatchClient(generatedClients.get(idx));
                        generatedClients.remove(idx);
                    }
                    else idx++;
                }

                Thread.sleep(200);
                // LOGGING
                logQueueStatus();
                checkForPeakHour();

                Thread.sleep(800);
                currentTime++;
                // verificam daca toti clientii au fost serviti
                if(generatedClients.size() == 0) {
                    ALL_CLIENTS_SERVED = true;
                    for (Server s : scheduler.getServerList()) {
                        if (!s.getQueue().isEmpty() || s.processingClient == true)
                            ALL_CLIENTS_SERVED = false;
                    }
                }
            }
            stopSimulation();
            logData();
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void generateRandomClients(){
        Random rand = new Random();
        savedGeneratedClients = new ArrayList<Client>();
        for(int i = 1; i <= noClients; i++){
            int randomArrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            int randomServiceTime = rand.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            Client client = new Client(i, randomArrivalTime, randomServiceTime);
            System.out.println("Client " + i + ":" + randomArrivalTime + ":" + randomServiceTime);
            savedGeneratedClients.add(client);
        }
    }

    public void loadGeneratedClients(){
        generatedClients = new ArrayList<Client>();
        for(Client c: savedGeneratedClients){
            Client newC = new Client(c.getId(), c.getArrivalTime(), c.getServiceTime());
            generatedClients.add(newC);
        }
    }

    public void startSimulation(){
        Thread t = new Thread(this);
        t.start();
    }

    public void stopSimulation(){
        for(Thread t: scheduler.getThreadList()){
            t.interrupt();
        }
        logger.addLine("Simulation stopped");
    }


    private void checkForPeakHour(){
        int totalClientsWaiting = 0;
        for(Server s: scheduler.getServerList()){
            totalClientsWaiting += s.getQueue().size();
            if(s.processingClient) totalClientsWaiting++;
        }
        if(totalClientsWaiting > peakHourClients){
            peakHourClients = totalClientsWaiting;
            peakHourStart = peakHourFinish = currentTime;
        }
        else if(totalClientsWaiting == peakHourClients && peakHourFinish == currentTime - 1){
            peakHourFinish++;
        }
    }
    public ArrayList<Client> getGeneratedClients(){
        return generatedClients;
    }

    public ArrayList<Client> getSavedGeneratedClients(){
        return savedGeneratedClients;
    }



    public void updateData(int maxSimulationTime, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int noClients, int noServers, SelectionPolicy strategy) {
        this.maxSimulationTime = maxSimulationTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.noClients = noClients;
        this.noServers = noServers;
        selectionPolicy = strategy;
    }

    private void logQueueStatus(){
        logger.add("Clienti in asteptare: ");
        for(Client c: generatedClients){
            logger.add(" (" + c.getId() + "," + c.getArrivalTime() + "," + c.getServiceTime() + ")");
        }
        logger.addLine("");
        for(Server s: scheduler.getServerList()){
            if(s.getQueue().size() != 0 || s.currentlyProcessedID != -1) {
                logger.add("   Coada " + s.getServerID() + "      Asteptand la coada: ");
                for (Client c : s.getQueue()) {
                    logger.add(" (" + c.getId() + "," + c.getArrivalTime() + "," + c.getServiceTime() + ")");
                }
                logger.addLine("");
                if (s.currentlyProcessedID != -1) {
                    logger.addLine("      In curs de procesare: " + s.currentlyProcessedID + "  Timp de finalizare: " + s.expectedFinishTime);
                }
            }
        }
    }
    public void logAverageTime(){
        float totalWaitingTime = 0;
        int totalClients = 0;
        for(Server s: scheduler.getServerList()){
            totalWaitingTime += (float)s.getTotalWaitingTime();
            totalClients += s.getTotalNumberOfClients();
        }
        logger.addLine("Average waiting time: " + (totalWaitingTime) / totalClients);
    }
    public void logAverageServiceTime(){
        float totalServiceTime = 0;
        int totalClients = 0;
        for(Server s: scheduler.getServerList()){
            totalServiceTime += (float)s.getTotalServiceTime();
            totalClients += s.getTotalNumberOfClients();
        }
        logger.addLine("Average service time: " + (totalServiceTime)/totalClients);
    }
    private void logPeakHour(){
        logger.addLine("Peak time interval: " + "[" + peakHourStart + "," + peakHourFinish + "]");
    }

    private void logData(){
        logAverageTime();
        logAverageServiceTime();
        logPeakHour();
    }
}
