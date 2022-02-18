package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
*{@summary Standard TextField for Formiko.}
*@author Hydrolien
*@lastEditedVersion 1.x
*/
public class FTextField extends JTextField {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor with a string &#38; default font.}
  *@lastEditedVersion 1.x
  */
  public FTextField(String s){
    super(s);
    setBorder(null);
    // setFontText(); //no need because it have been set as default font for all graphics components
  }
  public FTextField(){ this("");}
  // GET SET -------------------------------------------------------------------
  //setText et getText
  public void setBounds(int a, int b, int c){setBounds(a,b,c,FLabel.getDimY());}
  public void setFontText(Font fon){ this.setFont(fon);}
  public void setFontText(){ setFontText(Main.getFont1());}
  public void setFondTransparent(){setBackground(new Color(0,0,0,0));}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Update the size of this depending of text.}
  *@lastEditedVersion 2.6
  */
  public void updateSize(){
    setSize((int)(Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(getText()+" "))), (int)(getFont().getSize()*1.2));
  }
  /**
  *{@summary Add a listener to update this &#38; parent size so that this can still fit in.}<br>
  *@lastEditedVersion 2.19
  */
  public void addSizeUpdater(){
    getDocument().addDocumentListener(new DocumentListener() {
      /**
      *{@summary Update size.}<br>
      *@lastEditedVersion 2.19
      */
      @Override
      public void changedUpdate(DocumentEvent e) {
        update();
      }
      /**
      *{@summary Update size.}<br>
      *@lastEditedVersion 2.19
      */
      @Override
      public void removeUpdate(DocumentEvent e) {
        update();
      }
      /**
      *{@summary Update size.}<br>
      *@lastEditedVersion 2.19
      */
      @Override
      public void insertUpdate(DocumentEvent e) {
        update();
      }
      /**
      *{@summary Update size.}<br>
      *@lastEditedVersion 2.19
      */
      private void update(){
        // updateSize();
        // String text = getText();
        // setText(text+"  ")
        updateParentSize();
        // setText(text);
      }
    });
  }
  /**
  *{@summary Update parent size so that this can still fit in.}<br>
  *@lastEditedVersion 2.19
  */
  private void updateParentSize(){
    Component parent = getParent();
    while(parent.getParent()!=null && !(parent instanceof Window)){
      parent=parent.getParent();
    }
    if(parent instanceof Window && !(parent.equals(FPanel.getView().getF()))){
      ((Window)parent).pack();
    }
  }
}
