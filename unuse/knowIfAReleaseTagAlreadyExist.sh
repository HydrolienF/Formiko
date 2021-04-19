#!/bin/sh

# if [[ "$#" -ne "1" ]]; then
#   echo "Usage: $0 [version]"
# fi

version=$1
git ls-remote --heads --tags | grep -E "refs/(heads|tags)/${version}$" >/dev/null

if [[ "$?" -eq "0" ]]; then
  echo "1"
else
  echo "0"
fi
