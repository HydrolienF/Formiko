"""Translates text into the target language.

Target must be an ISO 639-1 language code.
See https://g.co/cloud/translate/v2/translate-reference#supported_languages
"""
import sys
from google.cloud import translate_v2 as translate
translate_client = translate.Client()

def affComplet():
    print(u"Text: {}".format(result["input"]))
    print(u"Translation: {}".format(result["translatedText"]))
    print(u"Detected source language: {}".format(result["detectedSourceLanguage"]))

def aff():
    print(":fr: "+text.rstrip())
    print(":en: "+result["translatedText"].rstrip())
    print(":eo: "+result2["translatedText"].rstrip())

def lire():
    fichier = open("out.txt", "r")
    text = fichier.read()
    #ligne=fichier.readline()
    fichier.close()
    return text


#if isinstance(text, six.binary_type):
#    text = text.decode("utf-8")
def remplace(s):
    s=s.replace("&quot;",'"')
    s=s.replace("&#39;","'")
    return s
# Text can also be a sequence of strings, in which case this method
# will return a sequence of results for each text.
def trad(s):
    result = translate_client.translate(text, target_language=s, source_language="fr")
    print(str(remplace(result["translatedText"])).rstrip())
#aff()
#text = lire()
text = ""
x=0
for ag in sys.argv:
    if x<2:
        x=x+1
    else :
        text = text +" "+ ag
if sys.argv[1]=="fr":
    print(text.rstrip())
else :
    trad(sys.argv[1])

#fichier2 = open("out2.txt", "a")
#fichier2.write(text)
#fichier2.write(result["translatedText"])
#fichier2.close()
