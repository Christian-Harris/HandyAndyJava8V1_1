package handler;

import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import menu.UserMenu;

/**
 *
 * @author Christian Harris
 */
public class ScrollInputHandler implements EventHandler<ScrollEvent>{
    private final UserMenu userMenu;
    
    public ScrollInputHandler(UserMenu userMenu){
        this.userMenu = userMenu;
    }
    
    @Override
    public void handle(ScrollEvent e){
        if(e.getDeltaY() > 0){
            if(this.userMenu.getCurrentInputPage() != 0){
                try{
                    this.userMenu.setCurrentInputPage(this.userMenu.getCurrentInputPage() - 1);
                    this.userMenu.setInputImage(SwingFXUtils.toFXImage(this.userMenu.getInputRenderer().renderImage(this.userMenu.getCurrentInputPage()), null));
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getDeltaY() < 0){
            if(this.userMenu.getCurrentInputPage() != this.userMenu.getInputDocument().getNumberOfPages() && this.userMenu.getCurrentInputPage() + 1 < this.userMenu.getInputDocument().getNumberOfPages()){
                try{
                    this.userMenu.setCurrentInputPage(this.userMenu.getCurrentInputPage() + 1);
                    this.userMenu.setInputImage(SwingFXUtils.toFXImage(this.userMenu.getInputRenderer().renderImage(this.userMenu.getCurrentInputPage()), null));
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
}
