package fr.formiko.formiko;

import fr.formiko.formiko.Message;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.structures.listes.Liste;

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
