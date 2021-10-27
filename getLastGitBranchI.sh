echo $(git branch -r --format="%(refname)" | grep -F $1'.' | awk '{ print length, $0 }' | sort -n -s | cut -d" " -f2- | tail -q -n 1 | tail -c +21)
