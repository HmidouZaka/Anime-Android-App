# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#
-keep class com.projectbyzakaria.animes.model.User {*;}
-keep class com.projectbyzakaria.animes.model.MovieLocal {*;}
-keep class com.projectbyzakaria.animes.data.dao.characters_search.* {*;}
-keep class com.projectbyzakaria.animes.data.dao.pepole.* {*;}
-keep class com.projectbyzakaria.animes.data.dao.top_anime_object.* {*;}
-keep class com.projectbyzakaria.animes.data.dao.top_mnga_object.* {*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.character_info.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.characters.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.epsedies.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.image_people.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.images.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.MovieDetail{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.images_characters.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.recommendations.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.reviews.*{*;}
-keep class com.projectbyzakaria.animes.data.dao.relotion.stuf.*{*;}

