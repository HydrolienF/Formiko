package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.g;

import java.util.List;
import java.util.ArrayList;

//TODO comments
/**
*{@summary Graphics view where user can choose Options.}
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public class FPanelOptions extends FPanel {
  private List<FPanelOptionsTab> tabList;
  private int currentTabId;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Default empty constructor hide.}
  *@lastEditedVersion 2.30
  */
  public FPanelOptions(){
    super();
    setVisible(false);
  }
  /**
  *{@summary Build this by adding tab.}
  *@lastEditedVersion 2.30
  */
  public void ini(){
    List<String> catList = Main.getFop().getListOfCat();
    tabList = new ArrayList<FPanelOptionsTab>(catList.size());
    for (String cat : catList) {
      FPanelOptionsTab pot = new FPanelOptionsTab(cat);
      tabList.add(pot);
      add(pot);
      // pot.centerInParent();
    }
  }
  // GET SET -------------------------------------------------------------------
  public int getItemWidth(){return getWidth();}
  public static int getItemHeight(){return Main.getFop().getInt("fontSizeText");}
  public static int getBetweenItemHeight(){return (int)(getItemHeight()*0.5);}
  public void setCurrentTabId(int id){
    currentTabId=id;
    setSize(getWidth(),tabList.get(currentTabId).getHeight());
    int k=0;
    for (FPanelOptionsTab pot : tabList) {
      pot.setVisible(k==currentTabId);
      k++;
    }
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    return super.toString() + "\nSelected:"+currentTabId+ "\n{" + tabList + "}";
  }
  // SUB-CLASS -----------------------------------------------------------------
  class FPanelOptionsTab extends FPanel {
    private List<FPanelOptionItem> opiList;
    public FPanelOptionsTab(String cat){
      super();
      List<String> listKey = Main.getFop().getListKeyFromCat(cat);
      opiList = new ArrayList<FPanelOptionItem>(listKey.size());
      int k=0;
      for(String key : listKey){
        addItem(key,k++);
      }
      setSize(getItemWidth(), (getItemHeight()+getBetweenItemHeight())*listKey.size());
    }
    // public String toString(){
    //   return super.toString()+"{"+opiList.toString()+"}";
    // }
    public void addItem(String key, int k){
      FPanelOptionItem opi;
      opi=new FPanelOptionItemString(key, Main.getFop().getString(key));
      opi.setLocation(0,k*(getItemHeight()+getBetweenItemHeight()));
      opiList.add(opi);
      add(opi);
    }
  }
  class FPanelOptionItem extends FPanel {
    private FLabel label;
    public FPanelOptionItem(String key){
      setSize(getItemWidth(), getItemHeight());
      label = new FLabel(g.get("oik."+key));
      label.setBounds(0,0,getWidth()/2, getHeight());
      add(label);
    }
  }
  public class FPanelOptionItemString extends FPanelOptionItem {
    private FTextField textField;
    public FPanelOptionItemString(String key, String value){
      super(key);
      textField = new FTextField(value);
      textField.setBounds(getWidth()/2,0,getWidth()/2, getHeight());
      add(textField);
    }
  }
}
