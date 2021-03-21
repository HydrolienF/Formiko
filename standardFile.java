package fr.formiko.x.x; //package have s at the end when class do not have.

import org.x.x;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.x.x;
import javax.x.x;

//default File since 1.39.0
//This file exist to be used as an exemple to respect standard of the Formiko projet.
//Some older part of the code may not respect this standard.
//x can be replace by any value.

//Naming use curent english word.

/**
*{@summary Used to define standard to use in Formiko.}<br>
*More informations about the class.<br>
*@author Hydrolien //author can be marked for the all class.
*@version 1.39.0 //version can be found in version.md
*/
public class ObjectStandard extends Parent implements Interface { //or : public class classStandard { if it isn't an Object.
  //var usuay don't have comment, but they can have some.
  private int var;
  /*** // 3 * for a var comment to be ingored in %age of commented code.
  *{@summary A name for ....}<br>
  *@version 1.39.0
  */
  private String var2Name;
  private Point point;
  public static boolean IS_STANDARD_OBJECT=true;

  // CONSTRUCTORS --------------------------------------------------------------
  // main construcor
  /**
  *{@summary The main construcor for ObjectStandard}<br>
  *More informations<br>
  *@param var A var used to ...
  *@param var2Name A String that ...
  *@version 1.39.0
  */
  public ObjectStandard(int var, String var2Name){
    //if extends Parent super(varUsedByParent);
    this.var=var;
    this.var2Name=var2Name;
    ini();
  }
  // secondary constructor (They all used the main construcor)
  //add a javadoc comment if needed.
  public ObjectStandard(int var){
    this(var,"default value for var2Name");
  }
  public ObjectStandard(){ // "{" are allways on the same line than function, for, if, while etc.
    this(0);
  }
  //if a comment is toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
  //long you can split it in several line.
  //Plz avoid to cut code line if there are not more than 100 char long.
  //Code line longer than 140 char long may be split.

  // GET SET -------------------------------------------------------------------
  //getters & setters don't have javadoc comment if they have only 1 line.
  public int getVar() {return var;}
  public void setVar(int var) {this.var = var;}
  public String getVar2Name() {return var2Name;}
  public void setVar2Name(String var2Name) {this.var2Name = var2Name;}

  // FUNCTIONS -----------------------------------------------------------------
  //function all have javadoc comments
  /**
  *{@summary Standard initialization function}<br>
  *More informations.<br>
  *@version 1.39.2 //version of the project the last time it have been edited.
  */
  public void ini(){
    //all thing that need to be done after launching the construcor.
  }
  public void functionToDoSomething(int x){
    var+=x/2;
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary A sub class commented to}<br>
  *More informations about the class<br>
  *@author Hydrolien
  *@version 1.38.2
  */
  public class MyClass {
    // CONSTRUCTORS --------------------------------------------------------------

    // GET SET -------------------------------------------------------------------

    // FUNCTIONS -----------------------------------------------------------------
  }
}
