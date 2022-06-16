package fr.formiko.usual.structures.listes;

import fr.formiko.formiko.Message;
import fr.formiko.usual.g;

import java.io.Serializable;

public class GMessage extends Liste<Message> implements Serializable {
  // FUNCTIONS -----------------------------------------------------------------
  public GString gmToGs(int x){
    GString gs = new GString();
    for (Message m : this) {
      gs.add(m.description());
      x--;
      if(x==0){
        gs.addTail("...");
        return gs;
      }
    }
    return gs;
  }
}
