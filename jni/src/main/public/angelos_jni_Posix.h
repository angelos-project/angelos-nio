/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class angelos_jni_Posix */

#ifndef _Included_angelos_jni_Posix
#define _Included_angelos_jni_Posix
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     angelos_jni_Posix
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_angelos_jni_Posix_close
  (JNIEnv *, jclass, jint);

/*
 * Class:     angelos_jni_Posix
 * Method:    read
 * Signature: (I[BJ)J
 */
JNIEXPORT jlong JNICALL Java_angelos_jni_Posix_read
  (JNIEnv *, jclass, jint, jbyteArray, jlong);

/*
 * Class:     angelos_jni_Posix
 * Method:    write
 * Signature: (I[BJ)J
 */
JNIEXPORT jlong JNICALL Java_angelos_jni_Posix_write
  (JNIEnv *, jclass, jint, jbyteArray, jlong);

/*
 * Class:     angelos_jni_Posix
 * Method:    lseek
 * Signature: (IJI)J
 */
JNIEXPORT jlong JNICALL Java_angelos_jni_Posix_lseek
  (JNIEnv *, jclass, jint, jlong, jint);

/*
 * Class:     angelos_jni_Posix
 * Method:    alloc
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_angelos_jni_Posix_alloc
  (JNIEnv *, jclass);

/*
 * Class:     angelos_jni_Posix
 * Method:    access
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_angelos_jni_Posix_access
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     angelos_jni_Posix
 * Method:    readlink
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_angelos_jni_Posix_readlink
  (JNIEnv *, jclass, jstring);

/*
 * Class:     angelos_jni_Posix
 * Method:    opendir
 * Signature: (Ljava/lang/String;)Langelos/jni/DIR;
 */
JNIEXPORT jobject JNICALL Java_angelos_jni_Posix_opendir
  (JNIEnv *, jclass, jstring);

/*
 * Class:     angelos_jni_Posix
 * Method:    readdir
 * Signature: (Langelos/jni/DIR;)[B
 */
JNIEXPORT jbyteArray JNICALL Java_angelos_jni_Posix_readdir
  (JNIEnv *, jclass, jobject);

/*
 * Class:     angelos_jni_Posix
 * Method:    closedir
 * Signature: (Langelos/jni/DIR;)I
 */
JNIEXPORT jint JNICALL Java_angelos_jni_Posix_closedir
  (JNIEnv *, jclass, jobject);

/*
 * Class:     angelos_jni_Posix
 * Method:    open
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_angelos_jni_Posix_open
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     angelos_jni_Posix
 * Method:    isLittleEndian
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_angelos_jni_Posix_isLittleEndian
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
