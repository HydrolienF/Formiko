package fr.formiko.views.gui2d;

import fr.formiko.usual.CheckFunction;
import fr.formiko.usual.Time;
import fr.formiko.usual.g;
import fr.formiko.usual.types.str;
import fr.formiko.views.gui2d.FComboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
*{@summary Personalised JDialog.}<br>
*Used to get a save name, get a creature id, get a food quantity etc.
*@author Hydrolien
*@lastEditedVersion 2.28
*/
public class FOptionPane extends JDialog {
  private FTextField textField;
  private FComboBox<String> comboBox;
  private FSlider slider;
  private FIntField intField;
  private int returnValue;
  private FButton bOk;
  private FButton bNotOk;
  private boolean greyOthers;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@param owner Frame that own this
  *@lastEditedVersion 2.17
  */
  public FOptionPane(Frame owner){
    super(owner, (String)null);
    // request focus & refuse to let user clic other component
    setModalityType(Dialog.ModalityType.APPLICATION_MODAL); //https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Dialog.ModalityType.html
    setUndecorated(true); //Remove the frame
    setVisible(false);
    setLayout(new FlowLayout());
    // alpha to 1 because javadoc say "Note that painting a pixel with the alpha value of 0 may or may not disable the mouse event handling on this pixel."
    setBackground(new Color(0,0,0,1));
    returnValue=-1;
    greyOthers=false;
  }
  /**
  *{@summary Constructor that use main frame as owner.}<br>
  *@lastEditedVersion 2.27
  */
  public FOptionPane(){
    this(FPanel.getView().getF());
  }
  /**
  *{@summary After have use all setter set visible.}<br>
  *@lastEditedVersion 2.27
  */
  public void build(){
    if(bOk==null){addOKButton();}
    getRootPane().setDefaultButton(bOk); //if enter is press it will launch this button.
    pack();
    if(FPanel.getView()!=null){
      setLocationRelativeTo(FPanel.getView().getF());
    }else{
      setLocationRelativeTo(null);
    }
    if(greyOthers){
      FPanel.getView().getPp().setTopColor(new Color(0,0,0,80));
    }
    setVisible(true);
    requestFocusInWindow();
    if(greyOthers){
      FPanel.getView().getPp().setTopColor(null);
    }
  }

  // GET SET -------------------------------------------------------------------
  public int getReturnValue() {return returnValue;}
	public void setReturnValue(int returnValue) {this.returnValue=returnValue;}
  public boolean isGreyOthers() {return greyOthers;}
	public void setGreyOthers(boolean greyOthers) {this.greyOthers=greyOthers;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Add an OK Button.}<br>
  *It will set return value to 1
  *@lastEditedVersion 2.27
  */
  public void addOKButton(){
    bOk = new FButton(" ✔ ", null, -2);
    bOk.setForeground(new Color(0,153,0));
    // bOk.setWithBackground(false);
    bOk.addActionListener(new ActionListener(){
      /**
      *{@summary Close the FOptionPane.}<br>
      *@lastEditedVersion 2.17
      */
      public void actionPerformed(ActionEvent e) {
        returnValue=1;
        disposeFOptionPane();
      }
    });
    add(bOk);
  }
  /**
  *{@summary Add a not OK Button.}<br>
  *It will set return value to 0
  *@lastEditedVersion 2.27
  */
  public void addNotOKButton(){
    bNotOk = new FButton(" ❌ ", null, -2);
    bNotOk.setForeground(Color.RED);
    // bNotOk.setWithBackground(false);
    bNotOk.addActionListener(new ActionListener(){
      /**
      *{@summary Launch action of a not OK Button.}<br>
      *@lastEditedVersion 2.27
      */
      public void actionPerformed(ActionEvent e) {
        onNotOkButtonPress();
      }
    });
    add(bNotOk);
  }
  /**
  *{@summary Action of a not OK Button.}<br>
  *@lastEditedVersion 2.27
  */
  public void onNotOkButtonPress(){
    returnValue=0;
    disposeFOptionPane();
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
    FLabel fl = new FLabel(g.getM(content));
    add(fl);
  }
  /**
  *{@summary Add an editable text field.}<br>
  *@param content default content
  *@lastEditedVersion 2.19
  */
  public void addField(String content){
    textField = new FTextField(g.getM(content));
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



  // static
  /**
  *{@summary print an alerte box.}
  *@param popUpMessage message of the popUp
  *@lastEditedVersion 2.28
  */
  public static void alerte(String popUpMessage){
    showMessageDialog(FPanel.getView().getF(), null, popUpMessage);
  }
  /**
  *{@summary Print a question box.}
  *@param popUpMessage message of the popUp
  *@return user answer
  *@lastEditedVersion 2.28
  */
  public static String question(String popUpMessage){
    FTextField tf = new FTextField(20);
    showMessageDialog(FPanel.getView().getF(), tf, popUpMessage);
    return tf.getText();
  }
  /**
  *{@summary Print a yes/no question box.}
  *@param popUpMessage message of the popUp
  *@param important some gui action will be done if true
  *@param cf a checkBox item that launch a function if checked
  *@return answer.
  *@lastEditedVersion 2.28
  */
  public static boolean questionYN(String popUpMessage, boolean important, CheckFunction cf){
    int r = showConfirmDialog(FPanel.getView().getF(), popUpMessage, important, cf);
    return r==1;
  }
  /**
  *{@summary Print a yes/no question box.}
  *@param popUpMessage message of the popUp
  *@param important some gui action will be done if true
  *@return answer.
  *@lastEditedVersion 2.27
  */
  public static boolean questionYN(String popUpMessage, boolean important){
    return questionYN(popUpMessage, important, null);
  }
  /**
  *{@summary Print a yes/no question box.}
  *@param popUpMessage message of the popUp
  *@return answer.
  *@lastEditedVersion 2.27
  */
  public static boolean questionYN(String popUpMessage){
    return questionYN(popUpMessage, false);
  }

  /**
  *{@summary Print a yes/no question box.}
  *@param parentComponent the owner of this
  *@param message message of the popUp
  *@param cf a checkBox item that launch a function if checked
  *@return answer.
  *@lastEditedVersion 2.27
  */
  public static int showConfirmDialog(Frame parentComponent, String message, boolean important, CheckFunction cf){
    FOptionPane op = new FOptionPane();
    op.addText(message);
    op.addOKButton();
    op.addNotOKButton();
    FPanelCheckFunction pcf=null;
    if(cf!=null){
      pcf = new FPanelCheckFunction(cf);
      op.add(pcf);
    }
    op.setGreyOthers(important);
    op.build();
    // String s=op.getContent();
    if(pcf!=null){
      pcf.run();
    }
    return op.getReturnValue();
  }
  /**
  *{@summary Print a message.}
  *@param parentComponent the owner of this
  *@param content content of this
  *@param message message of the popUp
  *@lastEditedVersion 2.27
  */
  public static void showMessageDialog(Frame parentComponent, Component content, String message){
    FOptionPane op = new FOptionPane();
    op.addText(message);
    if(content!=null){
      op.add(content);
    }
    op.addOKButton();
    op.build();
    // String s=op.getContent();
    // return op.getReturnValue();
  }
}
