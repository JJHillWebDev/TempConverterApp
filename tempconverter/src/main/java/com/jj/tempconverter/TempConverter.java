package com.jj.tempconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
    The main class for the TempConverter application. Launches a JavaFx 
    application on the Spring Boot framework.

    @author Jeremy Hill
    @version 16.0.1
 */
@SpringBootApplication
public class TempConverter extends Application 
{

    private ConfigurableApplicationContext context;	//holds application class
    private Parent rootNode;						//holds the fxml file


    /**
     * The main method launches the application.
     * @param args Used to launch application
     */
    public static void main(String[] args) 
    {
        Application.launch(args);
    }


    /**
     * The init method loads and sets the application class and fxml file.
     * @throws Exception
     */
    public void init() throws Exception 
    {
        context = SpringApplication.run(TempConverter.class);
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass()
                                .getResource("/fxml/TempConverter.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        rootNode = fxmlLoader.load();
    }


    /**
     * The start method takes a Stage object as an argument.
     * It also displays a new scene and gives the scene a title.
     * @param stage Stage object to display scene
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        // Build the scene graph.
        Scene scene = new Scene(rootNode);

        // Display our window, using the scene graph.
        stage.setTitle("Temperature Converter");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * The stop method closes the application.
     * @throws Exception
     */
    public void stop() throws Exception 
    {
        context.close();
    }
}