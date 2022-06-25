package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.erreur;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.Iterable;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboPopup;

/**
*{@summary extends of JComboBox<T> with Formiko colors.}
*@author Hydrolien
*@lastEditedVersion 2.2
*/
public class FComboBox<T> extends JComboBox<T> {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Constructors that use personalised color.}
  *@lastEditedVersion 2.2
  */
  public FComboBox(){
    super();
    personalise();
  }
  /**
  *{@summary Constructors that use personalised color.}
  *@lastEditedVersion 2.2
  */
  public FComboBox(T t []){
    super(t);
    personalise();
  }
  /**
  *{@summary Return a FComboBox with all the String in it.}<br>
  *@param gs the GString where to find all the possibility
  *@param defaultItem the default select item.
  *@lastEditedVersion 2.26
  */
  public static FComboBox<String> getFComboBox(Iterable gs, int defaultItem){
    FComboBox<String> cb = new FComboBox<String>();
    for (Object o : gs) {
      cb.addItem(o.toString());
    }
    if(defaultItem!=0){
      if(defaultItem>0 && defaultItem<cb.getItemCount()){
        cb.setSelectedIndex(defaultItem);
      }else{
        erreur.erreur("Unable to set "+defaultItem+"a item by default.");
      }
    }
    return cb;
  }
  /**
  *{@summary Return a FComboBox with all the String in it.}<br>
  *The default select item is the first one.
  *@param gs the GString where to find all the possibility
  *@lastEditedVersion 2.26
  */
  public static FComboBox<String> getFComboBox(Iterable gs){
    return getFComboBox(gs,0);
  }
  // GET SET -------------------------------------------------------------------
  public Object getSelectedItemReminder(){return selectedItemReminder;}
  /**
  *{@summary setSelectionBackground to sub component of this FComboBox.}
  *return true if it work without catching Exception.
  *@lastEditedVersion 2.2
  */
  public boolean setSelectionBackground(Color color){
    try {
      Object child = getAccessibleContext().getAccessibleChild(0);
      BasicComboPopup popup = (BasicComboPopup)child;
      JList list = popup.getList();
      list.setSelectionBackground(color);
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary personalise colors.}
  *@lastEditedVersion 2.2
  */
  public void personalise(){
    setForeground(Color.BLACK);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    setSelectionBackground(Main.getData().getButtonFocusColorWithoutAlpha());
    //TODO use personalise Border.
  }
}

// class CBEditor extends BasicComboBoxEditor {
//   public CBEditor(){
//
//   }
// }
