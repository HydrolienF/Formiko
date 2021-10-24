echo $(git branch --format="%(refname)" | grep 2. | tail -q -n 1 | tail -c +12)
