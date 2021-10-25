curentBranchCommit=$(./countCommitFromLastBranch.sh)
# branchVersion=$(git symbolic-ref HEAD --short)
branchVersion=$(./getLastGitBranch.sh)
echo $branchVersion'.'$curentBranchCommit > version.md
