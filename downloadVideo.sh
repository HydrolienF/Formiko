# To leave root user: "su - user"
vlc -I dummy --sout file/mp4:$1.mp4 $2 vlc://quit
