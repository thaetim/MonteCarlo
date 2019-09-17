package ms.montecarlo.view;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ms.montecarlo.Main;
import ms.montecarlo.model.DrawerTask;

import java.io.IOException;

import static javafx.application.Platform.exit;


public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private TextArea txa;
    @FXML
    private TextField txf;

    private DrawerTask task;
    @FXML
    private ProgressBar progress;

    private Main mainApp;

    public Controller(){

    }

    @FXML
    private void handleRunBtnAction(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        String numb = txf.getText();
        int n = Integer.parseInt(numb);
        task = new DrawerTask(gc, n);
        /*try {
            int n = Integer.parseInt(numb);
            task = new DrawerTask(gc, n);
        }
        catch (NumberFormatException e){
            System.err.println("TextField: NaN");
        }*/

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                int in = (Integer) task.getValue();
                double var = 16.*16.*(double)in/(double)n;
                txa.setText(Double.toString(var));
            }
        });
        new Thread(task).start();
        progress.progressProperty().bind(task.progressProperty());
    }

    public void setMainApp(Main mainApp){
        this.mainApp=mainApp;
        txf.setText("300000");
    }

    @FXML
    private void handleStopBtnAction(){
        task.cancel();
    }

    @FXML
    private void handleMenuExitAction(){
        exit();
    }
}
