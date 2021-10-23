# branchVersion=$(git symbolic-ref HEAD --short)
branchVersion=$(git branch --format="%(refname)" | grep 2. | tail -q -n 1 | tail -c +12)
branchLastVersion=""
k=true;
for i in `echo $branchVersion | tr "." " "`; do
  if [ $k = true ]; then
    k=false
    branchLastVersion=$i
  else
    branchLastVersion=$branchLastVersion"."$(($i-1))
  fi
done
echo $branchLastVersion
echo $branchVersion
totalCommit=$(git rev-list --count $branchVersion)
lastBranchTotalCommit=$(git rev-list --count $branchLastVersion)
curentBranchCommit=$(($totalCommit-$lastBranchTotalCommit))
echo $curentBranchCommit
