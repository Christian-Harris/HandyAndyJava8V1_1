package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import application.HandyAndyApplication;

/**
 *
 * @author Christian Harris
 */
public class LogoutHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent e){
        HandyAndyApplication.setCurrentUser(null);
        HandyAndyApplication.setSceneLoginMenu();
    }
}
