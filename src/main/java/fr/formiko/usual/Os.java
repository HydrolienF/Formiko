package fr.formiko.usual;

/**
*{@summary Do OS search.}<br>
*id (var that represent OS) can be used to do special actions like graphics action that are need to work on a specific OS as Windows that do weard thing with java Panel.<br>
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class Os {
  /** Os depending of user OS */
  private static Os os;
  /***
  *{@summary Save OS.}<br>
  *0=linux, 1=Windows, 2=Mac, -1=unknowOS
  */
  private byte id;
  protected static String osName = System.getProperty("os.name").toLowerCase(); //po for test
  private static String ARCH = System.getProperty("os.arch").toLowerCase();
  // CONSTRUCTORS --------------------------------------------------------------
  public Os(){iniOs();}
  // GET SET -------------------------------------------------------------------
  public static Os getOs(){
    if(os==null){
      os=new Os();
    }
    return os;
  }
  public static void setOs(Os o){os=o;}
  public byte getId(){return id;}
  public void setId(byte x){id=x;}
  public boolean isWindows(){return getId()==1;}
  public boolean isLinux(){return getId()==0;}
  public boolean isMac(){return getId()==2;}
  public String getARCH(){return ARCH;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *Return the name of the OS.
  */
  public String toString(){
    return(g.get("os."+id));
  }
  /**
  *Check if OS is WindowsOs.
  */
  private static boolean iniIsWindows() {
    return (osName.indexOf("win") >= 0);
  }
  /**
  *Check if OS is MacOs.
  */
  private static boolean iniIsMac() {
    return (osName.indexOf("mac") >= 0);
  }
  /**
  *Check if OS is Unix (linux).
  */
  private static boolean iniIsUnix() {
    return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") >= 0 );
  }
  /**
  *Save the OS in the int id.
  */
  public void iniOs(){
    if(ARCH.equals("amd64")){
      ARCH = "x86_64";
    }
    if(iniIsWindows()){
      setId((byte)1);
    }else if(iniIsUnix()){
      setId((byte)0);
    }else if(iniIsMac()){
      setId((byte)2);
    }else{
      setId((byte)-1);
    }
  }
}
