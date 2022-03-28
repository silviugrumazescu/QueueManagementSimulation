import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView {

    public JTextField minServiceTimeTextField, maxServiceTimeTextField, minArrivalTimeTextField, maxArrivalTimeTextField, maxSimulationTimeTextField, noClientsTextField, noServersTextField;
    JButton generateClientsButton, startSimulationButton;
    JList generatedClientsList;
    JComboBox strategyComboBox;
    DefaultListModel<String> generatedClientsListModel;
    SimulationManager simulationManager;
    JTextArea consoleTextArea;
    public MainView(){

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(new JLabel("Min Service Time: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(new JLabel("Max Service Time: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(new JLabel("Min Arrival Time: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(new JLabel("Max Arrival Time: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(new JLabel("Max Simulation Time: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        mainPanel.add(new JLabel("No clients "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        mainPanel.add(new JLabel("No Servers: "),c);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        mainPanel.add(new JLabel("Strategy:"), c);


        // text fields
        c.gridx = 1;
        c.gridy = 0;
        minServiceTimeTextField = createNewTextField();
        mainPanel.add(minServiceTimeTextField,c);
        c.gridx = 1;
        c.gridy = 1;
        maxServiceTimeTextField = createNewTextField();
        mainPanel.add(maxServiceTimeTextField,c);
        c.gridx = 1;
        c.gridy = 2;
        minArrivalTimeTextField = createNewTextField();
        mainPanel.add(minArrivalTimeTextField,c);
        c.gridx = 1;
        c.gridy = 3;
        maxArrivalTimeTextField = createNewTextField();
        mainPanel.add(maxArrivalTimeTextField,c);
        c.gridx = 1;
        c.gridy = 4;
        maxSimulationTimeTextField = createNewTextField();
        mainPanel.add(maxSimulationTimeTextField,c);
        c.gridx = 1;
        c.gridy = 5;
        noClientsTextField = createNewTextField();
        mainPanel.add(noClientsTextField,c);
        c.gridx = 1;
        c.gridy = 6;
        noServersTextField = createNewTextField();
        mainPanel.add(noServersTextField,c);
        c.gridx = 1;
        c.gridy = 7;
        c.fill = GridBagConstraints.HORIZONTAL;
        strategyComboBox = new JComboBox();
        strategyComboBox.addItem("Shortest Time Strategy");
        strategyComboBox.addItem("Shortest Queue Strategy");
        mainPanel.add(strategyComboBox, c);

        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 0, 0);
        generateClientsButton = new JButton("Generate clients");
        mainPanel.add(generateClientsButton, c);

        c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 7;
       // c.anchor = GridBagConstraints.FIRST_LINE_START;
        mainPanel.add(createNewList(), c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.insets = new Insets(20, 0, 0, 0);
        startSimulationButton = new JButton("Start simulation");
        mainPanel.add(startSimulationButton, c);

        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 3;
        c.gridheight = 5;
        //c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(createNewTextArea(), c);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setSize(800,600);
    }

    private JScrollPane createNewList(){
        generatedClientsListModel = new DefaultListModel<String>();
        generatedClientsList = new JList(generatedClientsListModel);
        JScrollPane sp = new JScrollPane();
        sp.setViewportView(generatedClientsList);
        generatedClientsList.setLayoutOrientation(JList.VERTICAL);
        return sp;
    }

    private JScrollPane createNewTextArea(){
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(consoleTextArea);
        sp.setPreferredSize(new Dimension(200, 300));
        return sp;
    }

    private JTextField createNewTextField(){
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(170,20));
        return f;
    }

    public void setGenerateClientsActionListener(ActionListener a){
        generateClientsButton.addActionListener(a);
    }
    public void setStartSimulationActionListener(ActionListener a){
        startSimulationButton.addActionListener(a);
    }

    public void addConsole(String line){
        consoleTextArea.append(line);
    }
}
