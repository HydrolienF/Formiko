version=$(git symbolic-ref HEAD --short)
if [[ -e ./journalDesMaj.md ]]; then
  num=$(grep -c "#Version 1."$version ./journalDesMaj.md)
else
  nom=0
fi
echo -n "Formiko1."$version"."$num
