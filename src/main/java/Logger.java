import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private MainView view;
    private String logFilePath = "log.txt";
    private File file;
    public Logger(MainView view){
        this.view = view;
        // create log file
        file = new File(logFilePath);


    }
    public synchronized void addLine(String line){
        view.addConsole(line + '\n');
        try{
            FileWriter writer = new FileWriter(file, true);
            writer.write(line + '\n');
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public synchronized void add(String text){
        view.addConsole(text);
        try{
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void clearLogFile(){
        file.delete();
        try{
            file.createNewFile();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
