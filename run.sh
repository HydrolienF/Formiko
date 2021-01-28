#command to launch the game
#exemple of use : ./run.sh -d
for param in "$*"
do
  java -cp target/classes/:. fr.formiko.formiko.Main $param
done
