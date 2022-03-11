search=$1
replace=$2
if [[ $search != "" && $replace != "" ]]; then
  sed -i "s/$search/$replace/gi" $3
fi
