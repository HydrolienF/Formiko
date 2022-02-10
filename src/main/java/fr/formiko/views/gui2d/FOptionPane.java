package fr.formiko.views.gui2d;

import fr.formiko.usuel.types.str;
import fr.formiko.views.gui2d.FComboBox;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
*{@summary Personalised JDialog.}<br>
*Used to get a save name, get a creature id, get a food quantity.
*@author Hydrolien
*@lastEditedVersion 2.17
*/
public class FOptionPane extends JDialog {
  private FTextField textField;
  private FComboBox<String> comboBox;
  private FSlider slider;
  private FIntField intField;
  /**
  *{@summary Main constructor.}<br>
  *@param owner Frame that own this
  *@lastEditedVersion 2.17
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
  *@lastEditedVersion 2.17
  */
  public void build(){
    FButton b = new FButton(" ✔ ", null, -1);
    b.addActionListener(new ActionListener(){
      /**
      *{@summary Close the FOptionPane.}<br>
      *@lastEditedVersion 2.17
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
  *@lastEditedVersion 2.17
  */
  public void disposeFOptionPane(){
    setVisible(false);
    dispose();
  }
  /**
  *{@summary Add an non-editable text field.}<br>
  *@param content default content
  *@lastEditedVersion 2.17
  */
  public void addText(String content){
    if(content==null || content.equals("")){return;}
    FLabel fl = new FLabel(content);
    add(fl);
  }
  /**
  *{@summary Add an editable text field.}<br>
  *@param content default content
  *@lastEditedVersion 2.19
  */
  public void addField(String content){
    textField = new FTextField(content);
    textField.addSizeUpdater();
    add(textField);
  }
  /**
  *{@summary Add an editable int field.}<br>
  *@param min the min value
  *@param max the max value
  *@param value default value
  *@lastEditedVersion 2.19
  */
  public void addIntField(int min, int max, int value){
    intField = FIntField.newFIntField(min, max, value);
    intField.addSizeUpdater();
    add(intField);
  }
  /**
  *{@summary Add a combo box.}<br>
  *@param content content of the combo box
  *@lastEditedVersion 2.17
  */
  public void addComboBox(String content []){
    comboBox = new FComboBox<String>(content);
    add(comboBox);
  }
  /**
  *{@summary Add a slider.}<br>
  *@param min the min value
  *@param max the max value
  *@param value the curent value
  *@lastEditedVersion 2.17
  */
  public void addSlider(int min, int max, int value){
    slider = new FSlider(min, max, value);
    add(slider);
  }
  /**
  *{@summary Add a slider &#38; an int field.}<br>
  *The 2 are connected.
  *@param min the min value
  *@param max the max value
  *@param value the curent value
  *@lastEditedVersion 2.17
  */
  public void addSliderAndIntField(int min, int max, int value){
    addSlider(min, max, value);
    addIntField(min, max, value);
    slider.addChangeListener(new ChangeListener() {
      /**
      *{@summary Update intField to.}<br>
      *@lastEditedVersion 2.17
      */
      @Override
      public void stateChanged(ChangeEvent event) {
        intField.setValue(slider.getValue());
      }
    });
    intField.addPropertyChangeListener(new PropertyChangeListener() {
      /**
      *{@summary Update slider to.}<br>
      *@lastEditedVersion 2.17
      */
      @Override
      public void propertyChange(PropertyChangeEvent event) {
        if(intField.getValue()==null){
          slider.setValue(0);
        }else{
          slider.setValue(str.sToI(intField.getValue().toString()));
        }
      }
    });
  }
  /**
  *{@summary Return the content of the text field.}<br>
  *@lastEditedVersion 2.17
  */
  public String getContent(){
    if(textField!=null){return textField.getText();}
    else if(comboBox!=null){return (String)comboBox.getSelectedItem();}
    else if(slider!=null){return ""+slider.getValue();}
    else{return null;}
  }
}
