#Main test
#java -cp build/main/:. fr.formiko.formiko.Main test
while read ligne
do
  echo $ligne
  java -cp ~/Formiko/junit-4.13.1.jar:.:build/main:build/test junit.textui.TestRunner $ligne
   #javaj ligne
done < testJunit.txt
