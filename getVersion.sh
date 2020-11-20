version=0
while [ -d "../Formiko"$version ]
do
  ((version=$version + 1))
done
((version=$version - 1))
if [[ version==0 ]]; then
  cd ..
  version=$(./getVersion.sh)
fi
echo $version
