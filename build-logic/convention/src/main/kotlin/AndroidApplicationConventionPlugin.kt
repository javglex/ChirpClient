import com.android.build.api.dsl.ApplicationExtension
import com.skymonkey.chirp.convention.configureKotlinAndroid
import com.skymonkey.chirp.convention.findVersionInt
import com.skymonkey.chirp.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                namespace = "com.skymonkey.chirpclient"

                defaultConfig {
                    applicationId = "com.skymonkey.chirpclient"
                    targetSdk = libs.findVersionInt("projectTargetSdkVersion")
                    versionCode = 1
                    versionName = "1.0"
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }

                configureKotlinAndroid(this)
            }
        }
    }
}