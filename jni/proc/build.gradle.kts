import org.gradle.kotlin.dsl.accessors.runtime.addConfiguredDependencyTo
import org.gradle.kotlin.dsl.provider.inClassPathMode
import org.gradle.kotlin.dsl.resolver.fetchKotlinBuildScriptModelFor
import org.gradle.kotlin.dsl.support.classFilePathCandidatesFor

/*val javaHome = System.getenv("JAVA_HOME")

plugins {
    `cpp-library`
}

configurations{
    create("jniproc") {
        isCanBeResolved = true
        isCanBeConsumed = true
    }
}



library {
    binaries.configureEach {

        val compileTask = compileTask.get()
        //println(buildTypes.)

        artifacts{
            add("jniproc", compileTask)
        }

        compileTask.includes.from("$javaHome/include")

        val osFamily = targetPlatform.targetMachine.operatingSystemFamily
        when{
            osFamily.isMacOs-> {
                compileTask.includes.from("-I$javaHome/include/darwin")
                compileTask.compilerArgs.add("-I/Library/Developer/CommandLineTools/SDKs/MacOSX10.15.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/")
                dependencies{ "jniproc"(files("$buildDir/lib/main/debug/lib${baseName}.dylib")) }
            }
            osFamily.isLinux -> {
                compileTask.includes.from("$javaHome/include/linux")
                dependencies{ "jniproc"(files("$buildDir/lib/main/debug/lib${baseName}.so")) }
            }
            osFamily.isWindows -> {
                compileTask.includes.from("$javaHome/include/win32")
                dependencies{ "jniproc"(files("$buildDir\\lib\\main\\debug\\${baseName}.dll")) }
            }
        }

        compileTask.source.setFrom(fileTree("src/main/cpp"))

        when(toolChain) {
            is VisualCpp -> compileTask.compilerArgs.addAll(listOf("/TC"))
            is Clang, is GccCompatibleToolChain -> compileTask.compilerArgs.addAll(listOf("-x", "c", "-std=c11"))
        }

    }
}

dependencies {
    implementation(project(":jni:base"))
}*/

plugins {
    base
    `cpp-library`
}

library {
    //val shared = components.withType(CppSharedLibrary::class)
    binaries.configureEach{
        base{
            archivesName.set("jniProc")
            //distsDirectory.set(layout.buildDirectory.dir("lib/main/release/"))
        }
        val compileTask = compileTask.get()

        compileTask.source.setFrom(fileTree("src/main/cpp"))

        val javaHome = System.getenv("JAVA_HOME")
        compileTask.includes.from("$javaHome/include")

        val osFamily = targetPlatform.targetMachine.operatingSystemFamily
        when{
            osFamily.isMacOs-> {
                compileTask.includes.from("-I$javaHome/include/darwin")
                compileTask.compilerArgs.add("-I/Library/Developer/CommandLineTools/SDKs/MacOSX10.15.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/")
            }
            osFamily.isLinux -> {
                compileTask.includes.from("$javaHome/include/linux")
            }
            osFamily.isWindows -> {
                compileTask.includes.from("$javaHome/include/win32")
            }
        }

        /*println(compileTask.asDynamicObject) */
        when(toolChain) {
            is VisualCpp -> compileTask.compilerArgs.addAll(listOf("/TC"))
            is Clang, is GccCompatibleToolChain -> compileTask.compilerArgs.addAll(listOf("-x", "c", "-std=c11"))
        }
    }
}

configurations {
    create("jniProc"){
        isCanBeResolved = true
        isCanBeConsumed = true
        isTransitive = true
        isVisible = true
        attributes {
            //attribute(Category.CATEGORY_ATTRIBUTE, named(Category.LIBRARY))
        }
    }

    create("jniProcDebug"){
        isCanBeResolved = true
        isCanBeConsumed = true
        isTransitive = true
        isVisible = true
    }
}

dependencies {
    //"jniProc"(fileTree(layout.buildDirectory.dir("lib/main/release/stripped")))
    //"jniProcDebug"(fileTree(layout.buildDirectory.dir("lib/main/debug")))
}

artifacts {
    add("jniProc", fileTree(layout.buildDirectory.dir("lib/main/release/stripped")).dir)
    add("jniProcDebug", fileTree(layout.buildDirectory.dir("lib/main/release/stripped")).dir)
}

// https://github.com/gradle/native-samples/blob/master/cpp/prebuilt-binaries/build.gradle