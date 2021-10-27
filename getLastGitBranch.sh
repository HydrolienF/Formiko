i=0
s="0.1"
sol=""
while [[ ! -z $s ]]; do
  sol=$s
  s=$(./getLastGitBranchI.sh $i)
  i=$((i+1))
done
echo $sol
