# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-optimizationpasses 5
-dontusemixedcaseclassnames  #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses  #指定不去忽略非公共的库类
-dontpreverify #不预校验
-keepattributes *Annotation*  #保留注释
-keepattributes Signature  #保留签名

-keepattributes *JavascriptInterface*
#不混淆第三方包
#-keep class com.badlogic.** {*;}
#AndroidManifest.xml调用或者webview的类
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.TabActivity
-keep public class * extends android.app.Dialog
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
#apk 包内所有 class 的内部结构
    -dump class_files.txt
    #未混淆的类和成员
    -printseeds seeds.txt
    #列出从 apk 中删除的代码
    -printusage unused.txt
    #混淆前后的映射
    -printmapping mapping.txt
    -dontwarn android.support.**
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
 #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }
#第三方类库
#so 文件
#-libraryjars libs/armeabi/libamapv304.so
#-libraryjars libs/armeabi/libamapv304ex.so
#-libraryjars libs/armeabi/libzbar.so
#gson
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.bgb.scan.model.** {*;}
-keep class sun.misc.Unsafe { *; }
#testinagent
-dontwarn com.testin.agent.**
-keep class com.testin.agent.** {*;}
-keep class com.nostra13.universalimageloader{ *; }
-keepclasseswithmembers class *{
    public *;
}


-dontwarn com.amap.api.**
-dontwarn org.apache.http.**
-dontwarn com.a.a.**

-dontwarn com.autonavi.**

-keep class com.amap.api.**  {*;}

-keep class com.autonavi.**  {*;}

-keep class com.a.a.**  {*;}

    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }

    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

     #保持 Serializable 不被混淆并且enum 类也不被混淆
        -keepclassmembers class * implements java.io.Serializable {
            static final long serialVersionUID;
            private static final java.io.ObjectStreamField[] serialPersistentFields;
            !static !transient <fields>;
            !private <fields>;
            !private <methods>;
            private void writeObject(java.io.ObjectOutputStream);
            private void readObject(java.io.ObjectInputStream);
            java.lang.Object writeReplace();
            java.lang.Object readResolve();
        }
         #不混淆资源类
 -keepclassmembers class **.R$* {
         public static <fields>;
}
# http client
 -keep class org.apache.http.** {*; }

 -keep class android-async-http-1.4.6.jar.**{*;}
 -dontwarn android.net.SSLCertificateSocketFactory

 -dontwarn com.umeng.**
 -keep class com.umeng*.** {*; }

 -dontwarn com.tbruyelle.rxpermissions.**
 -keep class com.tbruyelle.rxpermissions.**{*;}

 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
  long producerIndex;
  long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
  rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
  rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }

