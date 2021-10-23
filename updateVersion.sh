curentBranchCommit=$(./countCommitFromLastBranch.sh)
# branchVersion=$(git symbolic-ref HEAD --short)
branchVersion=$(git branch | grep 2. | tail -n 1)
echo $branchVersion'.'$curentBranchCommit > version.md
