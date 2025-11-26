-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

-keepclassmembers class com.mkulima.app.data.models.** {
    <init>(...);
    <fields>;
}

-keepclassmembers class com.mkulima.app.ui.viewmodel.** {
    <init>(...);
    <fields>;
}

-keep class io.github.supabase.** { *; }
-keep class io.ktor.** { *; }
-keep class org.jetbrains.** { *; }
-keep class kotlinx.serialization.** { *; }
