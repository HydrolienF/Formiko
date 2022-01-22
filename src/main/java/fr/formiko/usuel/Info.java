package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;

/**
*{@summary New info, warning, error class call to print message.}<br>
*It should replace erreur.java.<br>
*Example:<br>
*<code>new Info().setType(2).setClassDepth(3).setContent("wrong name").setFix("set to \" \"")</code><br>
*@author Hydrolien
*@version 2.17
*/
public class Info {
  enum InfoType {INFO, WARNING, ERROR;}
  private InfoType type;
  private String content;
  private String fix;
  private boolean fatal;
  private int classDepth;
  /**
  *{@summary Main contructor.}<br>
  *All variables should be set with setter or will stay at default value.
  *@version 2.17
  */
  public Info(){
    classDepth=1;
  }
  /**
  *{@summary Set type from an int.}<br>
  *0=info, 1=warning, 2=error.
  *@version 2.17
  */
  public Info setType(int i){
    switch(i){
      case 1:
        type=InfoType.WARNING;
        break;
      case 2:
        type=InfoType.ERROR;
        break;
      default:
        type=InfoType.INFO;
    }
    return this;
  }
	public Info setType(InfoType type) {this.type=type; return this;}
	public Info setContent(String content) {this.content=content; return this;}
	public Info setFix(String fix) {this.fix=fix; return this;}
	public Info setFatal(boolean fatal) {this.fatal=fatal; return this;}
  public Info setClassDepth(int classDepth) {this.classDepth=classDepth; return this;}
  /**
  *{@summary Main function that print this.}<br>
  *Print will be colored &#38; look like this:<br>
  *[INFO] $content. Fixed by : $fix
  *@version 2.17
  */
  public void print(){
    if(erreur.getMuet()){return;}
    if(type==null){type=InfoType.INFO;}
    if(fix==null){fix="";}
    String m = "";
    try {
      switch (type){
        case ERROR:
        if(!Main.getOp().getError()){return;}
        m=color.RED+g.get("erreur");
        break;
        case WARNING:
        if(!Main.getOp().getWarning()){return;}
        m=color.YELLOW+g.get("alerte");
        break;
        case INFO:
        if(!Main.getOp().getMessage()){return;}
        m=color.BLUE+g.get("info");
        break;
      }
      m="["+m.toUpperCase()+color.NEUTRAL+"] ";
      if(classDepth>0){
        m+="("+erreur.getCurentClassAndMethodName(classDepth)+") ";
      }
      if(content!=null){
        m+=str.sToSMaj(content)+".";
      }
      if(fix!=null){
        m+=g.get("erreur.6")+" : "+str.sToSMaj(fix)+".";
      }
    }catch (Exception e) {
      m="[null] ";
    }

    if(fatal){erreur.forceStop();}
  }
}
