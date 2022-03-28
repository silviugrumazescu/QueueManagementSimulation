import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {

    SimulationManager simulationManager;
    MainView view;

    public Controller(SimulationManager simulationManager, MainView view)
    {
        this.simulationManager = simulationManager;
        this.view = view;
        view.setGenerateClientsActionListener(new GenerateClientsEvent());
        view.setStartSimulationActionListener(new StartSimulationEvent());
    }

    public void updateSimulationManagerData(){
        int maxSimulationTime = Integer.parseInt(view.maxSimulationTimeTextField.getText());
        int minArrivalTime = Integer.parseInt(view.minArrivalTimeTextField.getText());
        int maxArrivalTime = Integer.parseInt(view.maxArrivalTimeTextField.getText());
        int minServiceTime = Integer.parseInt(view.minServiceTimeTextField.getText());
        int maxServiceTime = Integer.parseInt(view.maxServiceTimeTextField.getText());
        int noClients = Integer.parseInt(view.noClientsTextField.getText());
        int noServers = Integer.parseInt(view.noServersTextField.getText());
        SelectionPolicy strategy;
        if(view.strategyComboBox.getSelectedIndex() == 0){
            strategy = SelectionPolicy.SHORTEST_TIME;
        }
        else
            strategy = SelectionPolicy.SHORTEST_QUEUE;

        simulationManager.updateData(maxSimulationTime, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, noClients, noServers, strategy);
    }

    public class GenerateClientsEvent implements ActionListener {
        public void actionPerformed(ActionEvent e){
            updateSimulationManagerData();
            simulationManager.generateRandomClients();
            ArrayList<Client> list = simulationManager.getSavedGeneratedClients();
            view.generatedClientsListModel.clear();
            for(Client c: list){
                view.generatedClientsListModel.addElement("Client " + c.getId() + " arrival time: " + c.getArrivalTime() + " service time: " + c.getServiceTime());
                System.out.println("Client " + c.getId() + " arrival time: " + c.getArrivalTime() + " service time: " + c.getServiceTime());
            }
        }
    }
    public class StartSimulationEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            updateSimulationManagerData();
            simulationManager.startSimulation();
            simulationManager.logger.clearLogFile();
        }
    }

}
