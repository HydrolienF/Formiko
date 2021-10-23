# branchVersion=$(git symbolic-ref HEAD --short)
branchVersion=$(git branch | grep 2. | tail -n 1)
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
totalCommit=$(git rev-list --count $branchVersion)
lastBranchTotalCommit=$(git rev-list --count $branchLastVersion)
curentBranchCommit=$(($totalCommit-$lastBranchTotalCommit))
echo $curentBranchCommit
