package menu;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import handler.LogoutHandler;

/**
 *
 * @author Christian Harris
 */
public class UserMenu extends BorderPane{
    private static MenuBar menuBar;
    
    private static Menu fileMenu;
    private static MenuItem openFile;
    
    private static Menu userMenu;
    private static MenuItem logout;
    
    public UserMenu(){
        menuBar = new MenuBar();
        
        fileMenu = new Menu("File");
        openFile = new MenuItem("Open File");
        fileMenu.getItems().add(openFile);
        
        userMenu = new Menu("User");
        logout = new MenuItem("Logout");
        logout.setOnAction(new LogoutHandler());
        userMenu.getItems().add(logout);
        
        menuBar.getMenus().addAll(fileMenu, userMenu);
        
        this.setTop(menuBar);
    }
}
