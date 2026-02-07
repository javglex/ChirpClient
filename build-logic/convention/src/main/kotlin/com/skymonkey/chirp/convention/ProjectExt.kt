package com.skymonkey.chirp.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Helper methods to find version catalog entries from libs.toml.
 * Used by Convention plugins.
 */
val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun VersionCatalog.findVersionInt(variableName: String): Int =
    findVersion(variableName).get().toString().toInt()