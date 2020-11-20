#command to launch the game
#exemple of use : ./main.sh -d
for param in "$*"
do
  java -cp build/main/:. fr.formiko.formiko.Main $param
done
