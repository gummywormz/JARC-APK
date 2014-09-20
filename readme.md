JARC-APK
===
The goal of this project is to create a simple and portable APK builder for ARC and ARChon. The current builder is horribly clunky to even try to get installed. This is meant for the average person to just download the apk, set their options, and go.

Important Notes:
---
* You will most likely need a special version of Chrome called [Chrome Canary](http://www.google.com/intl/en/chrome/browser/canary.html) for the time being if you are using ARChon.
* 32 bit computers are not supported by ARChon at this time
* JRE 7 or later is required to run this application.
* Previously, I was unable to run java applications created by me on a Mac. It's prboably best to build from the source to avoid errors.

Instructions:
---

1 . __Chrome OS Users__: You must download one or more of the newly released Android apps available on the store. They are all currently on the featured ticker, so they should be easy to find. They apps are:
* [Vine](https://chrome.google.com/webstore/detail/vine/plfjlfohfjjpmmifkbcmalnmcebkklkh)
* [Duolingo](https://chrome.google.com/webstore/detail/duolingo/ebnhfamfopiobpaehmebmfjcgkaogihe)
* [EverNote](http://chrome.google.com/webstore/detail/dhfolfjkgpeaojbiicgheljefkfbbfkc)
* [Sight Words](https://chrome.google.com/webstore/detail/kids-sight-words/inpoiemibmljfjmjmlokfdllnkjejhai)
   
After installing them, you will receive the appropriate runtime that is needed.
   
__Chrome Browser / ARChon Users:__
   
Follow the instructions for installing [ARChon](https://github.com/vladikoff/chromeos-apk/blob/master/archon.md).
   
2 . Grab your APK file off of the play store by using something like [this](http://downloader-apk.com/)

3 . Create a new directory somewhere to store your converted apps

4 . Launch *JARC-APK* 

5 . Select which platform you which to build for, your replacement app slot (if you're using Chrome OS), resolution options, and the APK file you which to use.

6 . Press Build. If all is well, it will prompt you a location to save your converted folder in. The process will take a few seconds to complete. If it was not possible to automatically get the package name, you must enter it yourself. This is given in the application's URL on the Play Store, after the part where it says *?id*. For example, if the url is:

    https://play.google.com/store/apps/details?id=com.app.vodio&hl=en

then the package name is:

    com.app.vodio
    
7 . Load up *chrome://extensions* and load your unpacked extension. Press launch and you're done. If you get a "missing or invalid manifest" error, you chose the wrong directory. Go as deep as you can without hitting any files.

Credits:
---
[Vladikoff](https://github.com/vladikoff/chromeos-apk) for the original chromeOS-apk and template file.

[Joakime](https://github.com/joakime/android-apk-parser) for the Java APK parsing library.

License info is available in the appropriate files.