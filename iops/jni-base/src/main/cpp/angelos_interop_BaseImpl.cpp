/**
 * Copyright (c) 2021 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
#include <jni.h>
#include <string.h>
#include <errno.h>
#include "base.h"

#ifndef _Included_angelos_interop_Base
#define _Included_angelos_interop_Base
#ifdef __cplusplus
extern "C" {
#endif

static const char *JNIT_CLASS = "angelos/interop/Base";

/*
 * Class:     angelos_interop_Base
 * Method:    get_endian
 * Signature: ()I
 */
static jint get_endian(JNIEnv *env, jclass thisClass) {
    return endian();
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_platform
 * Signature: ()I
 */
static jint get_platform(JNIEnv *env, jclass thisClass) {
    return platform();
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_signal_abbreviation
 * Signature: (I)Ljava/lang/String;
 */
static jstring get_signal_abbreviation(JNIEnv *env, jclass thisClass, jint signum) {
    const char* abbr = signal_abbreviation(signum);
    return (*env)->NewStringUTF(env, abbr);
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_error
 * Signature: ()V
 */
static void get_error(JNIEnv * env, jclass thisClass){
    if (errno == 0)
        return;

    jclass proc = (*env)->FindClass(env, "angelos/sys/Error");
    jfieldID err_num = (*env)->GetStaticFieldID(env, proc, "errNum", "I");
    jfieldID err_msg = (*env)->GetStaticFieldID(env, proc, "errMsg", "Ljava/lang/String;");
    (*env)->SetStaticIntField(env, proc, err_num, errno);
    (*env)->SetStaticObjectField(env, proc, err_msg, (*env)->NewStringUTF(env, strerror(errno)));
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_event_poll
 * Signature: ()Langelos/io/poll/PollAction;
 */
static jobject get_event_poll(JNIEnv * env, jclass thisClass) {
    int description = -1;
    int action = -1;
    int err = 0;

    err = event_poll(&description, &action);
    if (err != 0)
        return NULL;

    jclass local_cls = (*env)->FindClass(env, "angelos/io/poll/PollAction");
    if (local_cls == NULL) // Quit program if Java class can't be found
        exit(1);

    jclass global_cls = (*env)->NewGlobalRef(env, local_cls);
    jmethodID cls_init = (*env)->GetMethodID(env, global_cls, "<init>", "(II)V");
    if (cls_init == NULL) // Quit program if Java class constructor can't be found
        exit(1);

    return (*env)->NewObject(env, global_cls, cls_init, description, action);
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_socket_attach
 * Signature: (I)I
 */
static jint get_socket_attach(JNIEnv *env, jclass thisClass, jint fd) {
    return socket_attach(fd);
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_stream_attach
 * Signature: (I)I
 */
static jint get_stream_attach(JNIEnv *env, jclass thisClass, jint fd) {
    return stream_attach(fd);
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_stream_is_open
 * Signature: (I)Z
 */
static jboolean get_stream_is_open(JNIEnv *env, jclass thisClass, jint fd) {
    switch(stream_is_open(fd)){
        case 1:
            return JNI_TRUE;
        default:
            return JNI_FALSE;
    }
}

/*
 * Class:     angelos_interop_Base
 * Method:    get_stream_close
 * Signature: (I)I
 */
static jint get_stream_close(JNIEnv *env, jclass thisClass, jint fd) {
    return stream_close(fd);
}

static JNINativeMethod funcs[] = {
        {"endian",   "()I", (void *) &get_endian},
        {"platform", "()I", (void *) &get_platform},
        {"signal_abbreviation", "(I)Ljava/lang/String;", (void *) &get_signal_abbreviation},
        {"get_error", "()V", (void *) &get_error},
        {"event_poll", "()Langelos/io/poll/PollAction;", (void *) &get_event_poll},
        {"socket_attach", "(I)I", (void *) &get_socket_attach},
        {"stream_attach", "(I)I", (void *) &get_stream_attach},
        {"stream_is_open", "(I)Z", (void *) &get_stream_is_open},
        {"stream_close", "(I)I", (void *) &get_stream_close},
};

#define CURRENT_JNI JNI_VERSION_1_6

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    jclass cls;
    jint res;

    (void) reserved;

    if ((*vm)->GetEnv(vm, (void **) &env, CURRENT_JNI) != JNI_OK)
        return -1;

    cls = (*env)->FindClass(env, JNIT_CLASS);
    if (cls == NULL)
        return -1;

    res = (*env)->RegisterNatives(env, cls, funcs, sizeof(funcs) / sizeof(*funcs));
    if (res != 0)
        return -1;

    init_event_handler();

    return CURRENT_JNI;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    jclass cls;

    (void)reserved;

    if ((*vm)->GetEnv(vm,(void **)&env, CURRENT_JNI) != JNI_OK)
        return;

    cls = (*env)->FindClass(env, JNIT_CLASS);
    if (cls == NULL)
        return;

    finalize_event_handler();

    (*env)->UnregisterNatives(env, cls);
}


#ifdef __cplusplus
}
#endif
#endif
