#version.md have 1 line that look like this "1.39.19"
version=$(cat version.md)
v1=${version:0:1}
v2=${version:2:2}
v3=${version:5}
vBranch=$(git symbolic-ref --short HEAD)
#if [$vBranch -eq $v2] || (echo $vBranch | only number == 0))
re='^[0-9]+$'
if ! [[ $vBranch =~ $re ]]; then
  echo "branch is not a number !"
fi
if [ "$vBranch" = "$v2" ]; then
  v3=$(($v3+1))
else
  v2=$vBranch
  v3=0
fi
echo $v1.$v2.$v3 > version.md
# mvn versions:set -DnewVersion=$v1.$v2.$v3 -q
git add version.md
echo "commit for "$v1.$v2.$v3
git commit
# message=""
# messageNull=""
# for param in "$*"; do
#   message+=" "$param
# done
# if ! [ $message != $messageNull ]; then
#   git commit -m $message
# else
#   git commit
# fi
