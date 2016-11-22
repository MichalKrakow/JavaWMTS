package sample;

import com.github.axet.wget.WGet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kamranzafar.jddl.*;


public class Controller{

    @FXML
    private Button showFolderPicker;

    @FXML
    private TextField folderPath;

    @FXML
    private Button startDownload;

    private static final Logger LOGGER = Logger.getLogger( Controller.class.getName() );

    @FXML
    private void handleButtonClick(ActionEvent event) {

        if (event.getSource() == showFolderPicker)
        {
            getFolderName();
        }

        if(event.getSource() == startDownload){
            //downloadSingleFile();
            downloadFile();

        }
    }

    @FXML
    private void handleTextFieldClick(MouseEvent event){
        if (event.getSource() == folderPath){
            getFolderName();
        }
    }

    private void downloadFile() {

        DirectDownloader dd = new DirectDownloader();

        String file = "http://14kdh.bialyszczep.pl/images/bg_1-a0a481633e.jpg";
        String out = "patryg.jpg";


        try {
            dd.download( new DownloadTask( new URL( file ), new FileOutputStream( out ), new DownloadListener() {
                String fname;
                int fileSize;

                public void onUpdate(int bytes, int totalDownloaded) {
                    System.out.println("got:" + bytes + " from:" + totalDownloaded);
                }

                public void onStart(String fname, int size) {
                    this.fname = fname;
                    this.fileSize = size;
                    System.out.println( "Downloading " + fname + " of size " + size );
                }

                public void onComplete() {
                    System.out.println( fname + " downloaded" );
                }

                public void onCancel() {
                    System.out.println( fname + " cancelled" );
                }
            } ) );
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }

        // Start downloading
        Thread t = new Thread( dd );
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private void getFolderName(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(Main.stage);

        if(selectedDirectory == null){
            folderPath.setText("No Directory selected");
        }else{
            folderPath.setText(selectedDirectory.getAbsolutePath());
        }
    }



}
