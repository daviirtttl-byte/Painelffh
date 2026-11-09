#include <jni.h>
#include <android/log.h>
#define LOG_TAG "G45Hack"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
extern "C" JNIEXPORT void JNICALL
Java_com_g45_ffnativo_OverlayService_toggleEsp(JNIEnv *env, jobject thiz, jboolean enable) {
    if (enable) LOGI("ESP ON (Shizuku ativo)");
}
