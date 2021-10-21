curentBranchCommit=$(./countCommitFromLastBranch.sh)
branchVersion=$(git symbolic-ref HEAD --short)
echo $branchVersion'.'$curentBranchCommit > version.md
