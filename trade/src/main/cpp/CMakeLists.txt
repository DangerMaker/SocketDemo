cmake_minimum_required(VERSION 3.4.1)

set(lib_DIR ${CMAKE_CURRENT_SOURCE_DIR})

add_library(libcrypto STATIC IMPORTED )

set_target_properties(libcrypto PROPERTIES IMPORTED_LOCATION ${lib_DIR}/openssl/lib/${ANDROID_ABI}/libcrypto.so)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

add_library( # Sets the name of the library.
             opensslLib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             GenerateKey.cpp
             ndk-helper.cpp
             ZlibDll.cpp
             native-jni.cpp
             aes_x86core.c
             )


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_include_directories(opensslLib PRIVATE
                           ${lib_DIR}/openssl/include
                           )

target_link_libraries( # Specifies the target library.
                       opensslLib
                       libcrypto
                       ${log-lib} )