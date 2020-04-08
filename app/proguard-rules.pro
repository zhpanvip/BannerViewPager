# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\edianzu\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class * extends androidx.fragment.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep public class * extends android.app.Activity
-keep class com.example.zhpan.circleviewpager.bean.** { *; }
-keep class com.example.zhpan.circleviewpager.net.** { *; }
-keep class com.zhpan.idea.** { *; }

-keep class androidx.recyclerview.widget.**{*;}
-keep class androidx.viewpager2.widget.**{*;}