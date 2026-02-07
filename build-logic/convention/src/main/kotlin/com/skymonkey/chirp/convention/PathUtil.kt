package com.skymonkey.chirp.convention

import org.gradle.api.Project
import java.util.Locale

// :core:data -> com.skymonkey.core.data
fun Project.pathToPackageName(): String {
    val relativePackageName = path
        .replace(':','.')
        .lowercase()
    return "com.skymonkey$relativePackageName"
}

// :core:data -> core_data
fun Project.pathToResourcePrefix(): String {
    val resourcePrefix = path
        .replace(':','_')
        .lowercase()
        .drop(1) + "_"
    return resourcePrefix
}

// :core:data -> CoreData
fun Project.pathToFrameworkName(): String {
    val parts = this.path.split(":", "-", "_", " ")
    val result = parts.joinToString("") { part ->
        part.replaceFirstChar {
            it.titlecase(Locale.ROOT)
        }
    }

    return result
}