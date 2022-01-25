package fr.formiko.views.gui2d;

import fr.formiko.views.gui2d.FComboBox;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
*{@summary Personalised JDialog.}<br>
*Used to get a save name, get a creature id, get a food quantity.
*@author Hydrolien
*@version 2.17
*/
public class FOptionPane extends JDialog {
  private FTextField textField;
  private FComboBox<String> comboBox;
  /**
  *{@summary Main constructor.}<br>
  *@param owner Frame that own this
  *@version 2.17
  */
  public FOptionPane(Frame owner){
    super(owner, (String)null);
    setModalityType(Dialog.ModalityType.APPLICATION_MODAL); //https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Dialog.ModalityType.html
    setUndecorated(true); //Remove the frame
    setVisible(false);
    setLayout(new FlowLayout());
    setBackground(new Color(0,0,0,0));
  }
  /**
  *{@summary After have use all setter, create the last button &#38; set visible.}<br>
  *@version 2.17
  */
  public void build(){
    FButton b = new FButton(" ✔ ", null, -1);
    b.addActionListener(new ActionListener(){
      /**
      *{@summary Close the FOptionPane.}<br>
      *@version 2.17
      */
      public void actionPerformed(ActionEvent e) {
        disposeFOptionPane();
      }
    });
    add(b);
    getRootPane().setDefaultButton(b); //if enter is press it will launch this button.
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    b.requestFocusInWindow();
  }
  /**
  *{@summary Close the FOptionPane.}<br>
  *@version 2.17
  */
  public void disposeFOptionPane(){
    setVisible(false);
    dispose();
  }
  /**
  *{@summary Add an editable text field.}<br>
  *@param content default content
  *@version 2.17
  */
  public void addField(String content){
    textField = new FTextField(content);
    add(textField);
  }
  /**
  *{@summary Add a combo box.}<br>
  *@param content content of the combo box
  *@version 2.17
  */
  public void addComboBox(String content []){
    comboBox = new FComboBox<String>(content);
    add(comboBox);
  }
  /**
  *{@summary Return the content of the text field.}<br>
  *@version 2.17
  */
  public String getContent(){
    if(textField!=null){return textField.getText();}
    else if(comboBox!=null){return (String)comboBox.getSelectedItem();}
    else{return null;}
  }
}
