# flutterkotlinmultiplatform

This is the library I wrote to integrate Kotlin Multiplatform to with Flutter to allow KMP to be used for the Business logic of an app with Flutterr used for its UI and to allow (somewhat) typesafe communication between the using protobufs.

## Integration process




### Android 
First I generated a new flutter module using the flutter create command

Tehn using information from the guide to integrate flutter into an existing app, i added 

```
maven("../fluttermodule/build/host/outputs/repo") // files from the generated module

   maven {
        setUrl("https://storage.googleapis.com/download.flutter.io")  // pointing to flutter repo
    }

```

to my gradle build file for my KMP Module along with 

```
implementation("com.fluttermodule.fluttermodule:flutter_debug:1.0")
```
in its androidMain dependencies to allow me to access the Flutter Android Framework

###IOS

To integrate The Flutter IOS Framework I used Kotlin/Natives CInterop feature to convert the objective-c code into Kotlin code.

I created a .def file called [flutternative.def](https://github.com/KweBoakye/FlutterKotlinMultiplatform/blob/main/shared/src/nativeinterop/cinterop/flutternative.def), definig the headers I wanted to pull in the language they were written in and the package name I would like the new "library" to have in kotlin. I opted to set CompilerOPts and linkerOPts in my gradle build file as I was unable to set the paths relatively, which causes issues when using the project in another machine. I also origannaly wanted to pull in the whole framework but I had issues with some aspects of it, luckily the relebvant parts i wanted based around methodChannles( Flutters way of communiating between Flutter and IOs/Android didnt have issues

I placed a copy of the Flutter Framework to [https://github.com/KweBoakye/FlutterKotlinMultiplatform/tree/main/shared/src/nativeinterop/sources] (https://github.com/KweBoakye/FlutterKotlinMultiplatform/tree/main/shared/src/nativeinterop/sources) from my Flutter IOS project as I found that when leaving it there would sometimes mean it was erased obviously causing build issues. 

Then in my gradle build file I created a cinterop task (which must have the same name as the relevant .def file) using ``` val flutternative by cinterops.creating``` in iosTarget compilation and used ```  includeDirs(
                        "${project.projectDir.parent}/shared/src/nativeinterop/sources/Flutter.framework/Headers"
                    )``` to point it to the relevant headers 
                    
As I was using the kotlin cocoapods tool to generate a pod from kmp library to allow it to be used in my flutter Ios App, I set te linkeropts [here](https://github.com/KweBoakye/FlutterKotlinMultiplatform/blob/aa7e2cb3065ebc2119c2f4073c9e82d959eaaca4/shared/build.gradle.kts#L87-L96) meaning that the code I had imported would properbe linked when generating the pod.

##KMP code

First I needed to create common representations of the types from the flutter framework I wanted to use in my KMP code using the [expect/actual](https://kotlinlang.org/docs/multiplatform-connect-to-apis.html) feature. For example for ```expect class CommonMethodChannel``` is ```expect class CommonMethodChannel``` on [Android](https://api.flutter.dev/javadoc/io/flutter/plugin/common/MethodChannel.html) and ```actual typealias CommonMethodChannel = FlutterMethodChannel``` on [iOS](https://api.flutter.dev/objcdoc/Classes/FlutterMethodChannel.html). 
