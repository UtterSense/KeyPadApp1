#
# Copyright (C) 2008 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_PACKAGE_NAME := KeyPadApp1
LOCAL_SDK_VERSION := current
LOCAL_PROGUARD_ENABLED := disabled


LOCAL_SRC_FILES := $(call all-java-files-under, java)
LOCAL_STATIC_JAVA_LIBRARIES := libkeypad libds3231


include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))

#---------------------------------------------------------------------
# Build from Prebuilt JAR file
#---------------------------------------------------------------------
include $(CLEAR_VARS)

LOCAL_MODULE := libkeypad
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_SDK_VERSION := current
LOCAL_SRC_FILES := libs/libkeypad.jar
LOCAL_UNINSTALLABLE_MODULE := true

include $(BUILD_PREBUILT)


#---------------------------------------------------------------------
# Build from Prebuilt JAR file
#---------------------------------------------------------------------
include $(CLEAR_VARS)

LOCAL_MODULE := libds3231
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_SDK_VERSION := current
LOCAL_SRC_FILES := libs/libds3231.jar
LOCAL_UNINSTALLABLE_MODULE := true

include $(BUILD_PREBUILT)




