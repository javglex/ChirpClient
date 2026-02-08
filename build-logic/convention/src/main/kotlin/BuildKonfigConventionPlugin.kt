import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.skymonkey.chirp.convention.pathToPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * A Gradle plugin that applies the BuildKonfig plugin and configures it for modular project setups.
 *
 * This plugin is designed to streamline the usage of the BuildKonfig plugin, which provides tools
 * for managing build-specific configurations in a Kotlin Multiplatform project.
 */
class BuildKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.codingfeline.buildkonfig")
            }

            extensions.configure<BuildKonfigExtension> {
                packageName = target.pathToPackageName()
                defaultConfigs {
                    // example of how to extract variables from local.properties
                    // and set them as build config fields
//                    val apiKey = gradleLocalProperties(rootDir, rootProject.providers)
//                        .getProperty("API_KEY")
//                        ?: throw IllegalStateException("API_KEY not found in local.properties")
//
//                    buildConfigField(FieldSpec.Type.STRING, "API_KEY", apiKey)
                }
            }
        }
    }
}