#command to launch a test.
#exemple of use : ./javaj.sh fr.formiko.usuel.liste.CStringTest fr.formiko.usuel.liste.GStringTest
for param in "$@"
do
  echo ==$param==
  java -cp junit-4.13.1.jar:.:build/main:build/test junit.textui.TestRunner $param
done
