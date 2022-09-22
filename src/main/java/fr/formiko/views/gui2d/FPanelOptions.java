package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

import java.util.List;

public class FPanelOptions extends FPanel {
  private List<FPanelOptionsTab> tabList;
  private int currentTabId;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelOptions(){
    super();
  }
  public void ini(){
    List<String> catList = Main.getFop().getListOfCat();
    for (String cat : catList) {
      FPanelOptionsTab pot = new FPanelOptionsTab(cat);
      tabList.add(pot);
      add(pot);
    }
  }
  // GET SET -------------------------------------------------------------------
  public static int getItemWidth(){return Main.getWidth()/2;}
  public static int getItemHeigth(){return Main.getWidth()/2;}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  class FPanelOptionsTab extends FPanel {
    public FPanelOptionsTab(String cat){
      super();
      List<String> listKey = Main.getFop().getListKeyFromCat(cat);
      for(String key : listKey){
        addItem(key);
      }
      setSize(getItemWidth()*listKey.size(), getItemHeigth());
      // TODO add location
    }
    public void addItem(String key){
      add(new FPanelOptionItemString(key, Main.getFop().getString(key)));
    }
  }
  class FPanelOptionItem extends FPanel {
    public FPanelOptionItem(String key){
      setSize(getItemWidth(), getItemHeigth());
      // TODO add FLabel
    }
  }
  public class FPanelOptionItemString extends FPanelOptionItem {
    public FPanelOptionItemString(String key, String value){
      super(key);
      // TODO add FTextField
    }
  }
}
