import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String args[]){

        MainView view = new MainView();
        SimulationManager simulation = new SimulationManager(new Logger(view));
        Controller c = new Controller(simulation, view);

       // Thread t = new Thread(simulation);
       // t.start();

    }


}
