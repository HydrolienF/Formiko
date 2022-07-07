package fr.formiko.fusual;

import fr.formiko.formiko.Main;
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
  public FFolder(Progression progression){
    super(progression);
    // ROOT_PATH
  }

  public static FFolder getFolder(){return (FFolder)Folder.getFolder();}
  public static void setFolder(FFolder f){Folder.setFolder(f);}

  public boolean userWantNewVersion(){
    if (!haveLastVersion()){
      // TODO #192 add a checkbox ignoreThisVersion & react to it.
      return Main.getView().popUpQuestionYN(g.get("newVersionAvailable.question"), true);
    }
    return false;
  }
}
