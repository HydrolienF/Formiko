package fr.formiko.formiko;

import fr.formiko.formiko.Message;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary List of Message.}<br>
*@author Hydrolien
*@lastEditedVersion 1.30
*/
public class GMessage extends Liste<Message> implements Serializable {
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return the List of Message as a List of String.}<br>
  *It will end by "..." if more message can't be show.<br>
  *If there is to much Message, only the firsts will be send.<br>
  *@param maxNumberOfMessage the max muber of Message to send back
  *@lastEditedVersion 1.30
  */
  public GString gmToGs(int maxNumberOfMessage){
    GString gs = new GString();
    for (Message m : this) {
      gs.add(m.description());
      maxNumberOfMessage--;
      if(maxNumberOfMessage==0){
        gs.addTail("...");
        return gs;
      }
    }
    return gs;
  }
}
