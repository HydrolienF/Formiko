#Generate the javadoc for the project in docs/javadoc

javadoc -doctitle "Javadoc of Formiko" -version -quiet -author -private -d docs/javadoc/ $(find src/main/java -name "*.java" | grep -v fr/formiko/usuel/tests/) -locale en_US -stylesheetfile docs/javadoc/styles.css -header '
<ul id="buttons-bar">
  <li class="mainButon" id="button-home-page"><a href="./index.html">Home</a></li>
  <li class="mainButon" id="button-download-page"><a href="./download.html">Download</a></li>
  <li class="mainButon" id="button-github-page"><a href="https://github.com/HydrolienF/Formiko/">Github</a></li>
  <li class="mainButon" id="button-javadoc-page"><a href="./Formiko/javadoc/index.html">Javadoc</a></li>
</ul>'
# -bottom
