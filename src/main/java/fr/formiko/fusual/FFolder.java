package fr.formiko.fusual;

import fr.formiko.usual.Folder;
import fr.formiko.usual.Progression;

/**
*{@summary Class that have all link to all folder of formiko.}<br>
*You can acces to file by using getters.
*Ex : getFolderStable()+getFolderImages() will return the path to stable images.
*@author Hydrolien
*@lastEditedVersion 2.26
*/
public class FFolder extends Folder {
  // private static FFolder folder;
  public FFolder(Progression progression){
    super(progression);
    // ROOT_PATH
  }

  public static FFolder getFolder(){return (FFolder)folder;}
  public static void setFolder(FFolder f){folder=f;}

  public boolean userWantNewVersion(){
    // TODO #192 if (!haveLastVersion())
    // TODO #192 ask if user want the new version. If it want exit(2);
    return true;
  }
}
