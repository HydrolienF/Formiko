#Generate the javadoc for the project in docs/javadoc

javadoc --allow-script-in-comments -encoding "utf-8" -docencoding "utf-8" -windowtitle "Javadoc of Formiko" -doctitle "Javadoc of Formiko" -version -quiet -author -private -d docs/javadoc/ $(find src/main/java -name "*.java") -locale en_US -stylesheetfile docs/javadoc/styles.css -header '
<ul id="buttons-bar">
  <li class="mainButon" id="button-home-page"><a href="https://formiko.fr/">Home</a></li>
  <li class="mainButon" id="button-download-page"><a href="https://formiko.fr/download">Download</a></li>
  <li class="mainButon" id="button-github-page"><a href="https://github.com/HydrolienF/Formiko/">Github</a></li>
  <li class="mainButon" id="button-javadoc-page"><a href="https://formiko.fr/Formiko/javadoc/">Javadoc</a></li>
</ul>'
# -classpath "/usr/share/maven/lib/"
# -top '<link rel="shortcut icon" type="image/png" href="https://formiko.fr/assets/logo.png">'
# -bottom
# $(find src/main/java -name "*.html")
