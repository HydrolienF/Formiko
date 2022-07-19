package fr.formiko.fusual;

import fr.formiko.formiko.Main;
import fr.formiko.usual.CheckFunction;
import fr.formiko.usual.Folder;
import fr.formiko.usual.Progression;
import fr.formiko.usual.g;

/**
*{@summary Class that have all link to all folder of formiko.}<br>
*You can acces to file by using getters.
*Ex : getFolderStable()+getFolderImages() will return the path to stable images.
*@author Hydrolien
*@lastEditedVersion 2.26
*/
public class FFolder extends Folder {
  /**
  *{@summary Main constructor that only call Folder one.}<br>
  *@lastEditedVersion 2.26
  */
  public FFolder(Progression progression){
    super(progression);
    // ROOT_PATH
  }

  public static FFolder getFolder(){return (FFolder)Folder.getFolder();}
  public static void setFolder(FFolder f){Folder.setFolder(f);}

  /**
  *{@summary Return true if user is ok to be ask next version &#38; answer yes.}<br>
  *@lastEditedVersion 2.26
  */
  public boolean userWantNewVersion(){
    if (!haveLastVersion() && !Main.getOp().getLastCheckedVersion().equals(getLastStableVersion())){
      CheckFunction cf = new CheckFunction(){
        /**
        *{@summary Update Options value if user have enable checkbox.}<br>
        *@lastEditedVersion 2.26
        */
        @Override
        protected void exec(){
          Main.getOp().setLastCheckedVersion(getLastStableVersion());
          Main.getOp().saveOptions();
        }
      };
      cf.setText(g.get("IgnoreThisVersion"));
      return Main.getView().popUpQuestionYN("newVersionAvailable.question", true, cf);
    }
    return false;
  }
}
