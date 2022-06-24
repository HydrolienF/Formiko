package fr.formiko.usual;

import fr.formiko.usual.erreur;
import fr.formiko.usual.types.str;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
*{@summary Fonts tools.}
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class Fonts {
  // GET SET -------------------------------------------------------------------
  private static String getFolderFont(){return Folder.getFolder().getFolderStable()+Folder.getFolder().getFolderBin()+"font/";}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Create a font located in game data.}<br>
  *@param fontName the font name to create
  *@return true if it work else false
  *@lastEditedVersion 2.25
  */
  public static boolean createFont(String fontName){
    if(getFolderFont()==null){return false;}
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    File file = new File(getFolderFont()+fontName+".otf");
    if(!file.exists()){
      file = new File(getFolderFont()+fontName+".ttf");
    }
    if(!file.exists()){return false;}
    Font font=null;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT,file);
    }catch (FontFormatException | IOException | NullPointerException | IllegalArgumentException | SecurityException e) {
      return false;
    }
    if(font==null){return false;}
    ge.registerFont(font);
    return true;
  }
  /**
  *{@summary Create a font located in game data.}<br>
  *@param familyFontName the family font name to create
  *@return true if we add at least 1 font
  *@lastEditedVersion 2.25
  */
  public static boolean createFonts(String familyFontName){
    if(getFolderFont()==null){return false;}
    String familyFontNameWithoutSpace = familyFontName.replaceAll(" ","");
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    File directory = new File(getFolderFont());
    if(directory==null || directory.listFiles()==null){return false;}
    boolean flag=false;
    for (File f : directory.listFiles()) {
      if((str.contient(f.getName(),familyFontName, 1) || str.contient(f.getName(),familyFontNameWithoutSpace, 1)) && (str.contient(f.getName(),".ttf", 2) || str.contient(f.getName(),".otf", 2))){
        Font font=null;
        try {
          font = Font.createFont(Font.TRUETYPE_FONT,f);
        }catch (FontFormatException | IOException | NullPointerException | IllegalArgumentException | SecurityException e) {}
        if(font!=null){
          flag=true;
          if(!ge.registerFont(font)){
            erreur.info("Font "+font.getName()+" alredy exist on the OS");
          }
        }
        // erreur.println(f+" "+font);
      }
    }
    return flag;
  }
}
