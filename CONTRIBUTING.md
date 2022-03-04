# How to contribute


I'm really glad you're reading this, because we need volunteer developers to help this project to grow.

If you haven't test the game yet, you can try it [there](https://formiko.fr/download.html).

## Setup project

You will need Java (JDK) 16 (or more) and Maven 3.6.3 (or more).

You need to have JAVA_HOME set on your computer:
  * On linux you can set JAVA_HOME by adding on ~/.bashrc `export JAVA_HOME=/usr/lib/jvm/java-16-...`
  * JAVA_HOME & Maven need to be set on PATH for Windows.

ps : some IDE provide Java & Maven.

## Compile & Run project

*Compile code:*
`mvn compile`

*Test code:*
`mvn test`

*Launch game*
`mvn exec:java`

*Launch game with args*
`mvn exec:java -Dargs="$args1 $args2"`

## Submitting changes

Never push your commit directly to master. Push it to a new branch that will be merge in a version named branch (as 2.20).

Commit should look like this one : `[#44] Remove moving glitch`. A link to the connected issue & a small description of what you have done.

## Coding conventions

All new classes names, functions names, vars names need to be in English.

Java basic naming conventions that you probably already know can be found [here](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)

In this project we have few more rules to shorter a bit code:
  * `{` Are at the end of the line. `public static void function(String arg1){`
  * tab are 2 spaces. `class C {
      function1(){
        System.out.println("Formiko !");
      }
    }`
  * getters & setters are write in 1 line. `public static int getMax(){return max;}`
