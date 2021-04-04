#command to launch the game on mac / linux
#exemple of use : ./run.sh -d
for param in "$*"
do
  java --module-path target/classes/:$PATH_TO_FX -cp target/classes/:. fr.formiko.formiko.Main $param
done
