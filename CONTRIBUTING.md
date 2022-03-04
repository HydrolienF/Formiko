# How to contribute


I'm really glad you're reading this, because we need volunteer developers to help this project to grow.

If you haven't test the game yet, you can try it [there](https://formiko.fr/download.html).

## Setup project

You need git, Java (JDK) 16 (or more) and Maven 3.6.3 (or more).

For apt package manager OS as Ubuntu or Debian:

```sudo apt -y install git```

```sudo apt -y install maven```

```sudo apt -y install openjdk-17-jdk```

For other OS, you will have some similar simple way to download this 3 tools (as .exe on Windows)

You can check your git, mvn, java & javac version by using :

`git --version; java --version; javac --version; mvn --version`

ps: some IDE provide git, Java & Maven.

Then you will need to clone the project:

`git clone git@github.com:HydrolienF/Formiko.git`

ps: If you haven't configure a key to connect your github account follow [this tutorial](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)

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
  * getters & setters are write in 1 line. `public static int getMax(){return max;}`
  * tab are 2 spaces.

```Java
class C {
  function1(){
    System.out.println("Formiko !");
  }
}
```
