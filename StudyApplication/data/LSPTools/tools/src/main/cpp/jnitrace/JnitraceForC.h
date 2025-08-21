

#ifndef QCONTAINER_PRO_APPUTILS_H
#define QCONTAINER_PRO_APPUTILS_H

#include <sys/syscall.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>
#include <malloc.h>
#include <regex>
#include <bits/getopt.h>
#include <asm/unistd.h>
#include <unistd.h>
#include <asm/fcntl.h>
#include "limits.h"
#include <string.h>
#include <cerrno>
#include <cstring>
#include <climits>
#include "syscall.h"
#include <cstring>
#include <cstdio>
#include <unistd.h>
#include <stdlib.h>
#include <syscall.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <unistd.h>
#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>
#include <malloc.h>
#include <regex>
#include <bits/getopt.h>
#include <asm/unistd.h>
#include <unistd.h>
#include <asm/fcntl.h>
#include <fcntl.h>
#include <string>
#include <list>
#include <sstream>
#include <ostream>
#include <iostream>
#include <fstream>


#include "logging.h"
#include "lsp.h"



using namespace std;

class Jnitrace {
    public:
        /**
         * start jni trace
         *
         * @param env  jniEnv
         * @param soname using the list to save the entire collection, the jnitrace only handles the so name inside the list
         * @param isSave  Whether to save the file, save the file is the incoming path, otherwise pass null
         */
        static void startjnitrace(JNIEnv * env, const std::list<string>& soname,char * isSave, HookFunType hook_func);
        /**
         * stop jni trace
         */
        [[maybe_unused]] static void stopjnitrace();
};


#endif //QCONTAINER_PRO_APPUTILS_H
