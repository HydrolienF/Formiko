# TODO with cmd.md git rev-list --count master
branchVersion=$(git symbolic-ref HEAD --short)
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
