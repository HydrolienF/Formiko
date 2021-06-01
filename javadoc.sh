#Generate the javadoc for the project in docs/javadoc

javadoc -doctitle "Javadoc of Formiko" -version -quiet -author -private -d docs/javadoc/ $(find src/main/java -name "*.java" | grep -v fr/formiko/usuel/tests/) -locale en_US -stylesheetfile styles.css -header '
<h1 id="main-title">Formiko</h1>
<nav>
  <ul id="buttons-bar">
    <li id="button-home-page"><a href="./index.html">Home</a></li>
    <li id="button-download-page"><a href="./download.html">Download</a></li>
    <li id="button-github-page"><a href="https://github.com/HydrolienF/Formiko/">Github</a></li>
    <li id="button-javadoc-page"><a href="./Formiko/javadoc/index.html">Javadoc</a></li>
  </ul>
</nav>'
# -bottom
