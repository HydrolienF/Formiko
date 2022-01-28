package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import java.awt.Insets;

/**
*{@summary Frame used for the launcher.}<br>
*The launcher can be used to show progress of download data at 1a launch,
*or to update game, or to download textures packs.<br>
*@author Hydrolien
*@lastEditedVersion 2.7
*/
public class FFrameLauncher extends FFrame {
  private FPanelLauncher pl;
  // private String downloadingMessage;
  private static boolean IS_UIMANAGER_INI;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *Frame is centered, small &#38; containt a FPanelLauncher.
  *@lastEditedVersion 2.7
  */
  public FFrameLauncher(){
    super("Formiko Launcher", (int)(getScreenWidth()/2.5), (int)(getScreenHeigth()/2.5), false);
    if(!IS_UIMANAGER_INI){iniUImanager();}
    pl = new FPanelLauncher(getWidth(), getHeight()-40,-10, 110);
    setContentPane(pl);
    endIni();
  }

  // GET SET -------------------------------------------------------------------
  public void setDownloadingMessage(String s){pl.l.setTexte(s);}
  public void setDownloadingValue(int x){pl.pb.setValue(x);}
  public void setButtonRetryVisible(boolean b){pl.buttonRetry.setVisible(b);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary initialize the UImanager for all ProgressBar.}<br>
  *@lastEditedVersion 2.7
  */
  public static void iniUImanager(){
    // UIManager.put("ProgressBar.background", Main.getData().getButtonColor(3));
    UIManager.put("ProgressBar.foreground", Main.getData().getButtonColor(0));
    // UIManager.put("ProgressBar.selectionBackground", Color.RED);
    // UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
    IS_UIMANAGER_INI=true;
  }
  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Main panel used for the launcher.}<br>
  *It containt a message, a progress bar, maybe an error message, maybe a button to try again.
  *@author Hydrolien
  *@lastEditedVersion 2.7
  */
  class FPanelLauncher extends FPanel {
    private JProgressBar pb;
    private FLabel l;
    private FButton buttonRetry;
    /**
    *{@summary Main constructor.}<br>
    *It initialize &#38; place item on the Panel.
    *@lastEditedVersion 2.7
    */
    public FPanelLauncher(int w, int h, int minProgressBar, int maxProgressBar) {
      super();
      setSize(w,h);
      pb = new JProgressBar(minProgressBar, maxProgressBar);
      pb.setSize((int)(getWidth()*0.95), getHeight()/12);
      pb.setLocation((int)(getWidth()*0.02), getHeight()/2);
      pb.setValue(minProgressBar);
      add(pb);
      l = new FLabel();
      l.setSize(pb.getSize());
      l.setLocation((int)(getWidth()*0.02), (int)(getHeight()/2-l.getHeight()*1.5));
      // l.setFondTransparent();
      // l.setOpaque(false);
      l.setBackground(Color.WHITE);
      add(l);
      buttonRetry = new FButton("retry download",this,1000);
      buttonRetry.setSize(getWidth()/2, getHeight()/12);
      buttonRetry.setLocation(0,getHeight()-(buttonRetry.getHeight()));
      buttonRetry.setVisible(false);
      add(buttonRetry);
    }
  }
}
