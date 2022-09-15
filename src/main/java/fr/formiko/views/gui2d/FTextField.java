package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

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
  *{@summary 1 main constructor with a string &#38; default font.}
  *@lastEditedVersion 2.28
  */
  public FTextField(String s){
    super(s);
    ini();
  }
  public FTextField(){ this("");}
  /**
  *{@summary 1 main constructor with a number of char to gess width &#38; default font.}
  *@param columns the number of columns to use to calculate the preferred width; if columns is set to zero, the preferred width will be whatever naturally results from the component implementation
  *@lastEditedVersion 2.28
  */
  public FTextField(int columns){
    super(columns);
    ini();
  }
  /**
  *{@summary Initialize at the end of the constructor.}
  *@lastEditedVersion 2.28
  */
  public void ini(){
    setBorder(null);
    // setFontText(); //no need because it have been set as default font for all graphics components
  }
  // GET SET -------------------------------------------------------------------
  //setText et getText
  public void setBounds(int a, int b, int c){setBounds(a,b,c,FLabel.getDimY());}
  public void setFontText(Font fon){ this.setFont(fon);}
  public void setFontText(){ setFontText(Main.getFontText());}
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
