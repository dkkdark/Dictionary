// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}