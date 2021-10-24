#command to launch the game on mac / linux
#exemple of use : ./run.sh -d
for param in "$*"
do
  # java -cp target/classes/:. fr.formiko.formiko.Main $param
  mvn -ntp exec:java -Dargs="$param"
done
