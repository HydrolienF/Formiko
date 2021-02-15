#create a jar file.
#exemple of use : ./jar.sh Formiko
cd target/classes/.;jar cfm $1.jar ../../manifest.txt *;mv $1.jar ../../.;cd ../..
