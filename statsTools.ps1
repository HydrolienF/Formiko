# It Save Data for every day where the projet have been update in res.txt. Last edit on the day is used.
# On 2022-02-22 only 64 day have been pass on the project over more than 1 year for almost 2000 commit.

$currentCommit=git log -1 --pretty=format:"%h"
rm res.csv
# touch res.csv
echo "Date;%age commented;commented/non-commented;class;public fct;protected fct;private function; 1 line fct;lines;file" >> res.csv
cd ../test/Formiko/
git checkout --force $currentCommit --quiet
cd ../../Formiko/
$outLine=$currentCommitDate
$outLine+=";"
$outLine+=java -jar F.jar statsG ../test/Formiko/src/main/ -q
echo $outLine >> res.csv
$k=0
Do{
  $lastCommitDate=$currentCommitDate
  $currentCommitDate=git log -1 --pretty=format:"%ad" --date=short $currentCommit
  # $futurCommitDate=git log -1 --pretty=format:"%ad" --date=short $currentCommit^
  # echo $currentCommitDate" & "$futurCommitDate" "(-Not $currentCommitDate -eq $futurCommitDate)
  If($currentCommitDate -eq $lastCommitDate){
  }Else{
    # echo $currentCommit
    $k+=1
    If($k%10 -eq 0){
      echo $k
    }
    cd ../test/Formiko/
    git checkout --force $currentCommit --quiet
    cd ../../Formiko/
    $outLine=$currentCommitDate
    $outLine+=";"
    $outLine+=java -jar F.jar statsG ../test/Formiko/src/main/ -q
    echo $outLine >> res.csv
    # echo $outLine
  }
  $currentCommit=git log -1 --pretty=format:"%h" $currentCommit^
}Until($currentCommit -eq "4e67a89b")
