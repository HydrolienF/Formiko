set /p version=< version.md
echo %version%
cscript addLaunchToMsi.js %version%
