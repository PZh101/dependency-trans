package com.github.dt.dependencytrans.model

import com.github.dt.dependencytrans.BuildType
import java.io.File

/**
 * @author zhoup
 * @since 2023/9/21
 */
data class DependModel(val groupId: String, val artifactId: String, val version: String) {
    /**
     * 变为文件路径
     */
    fun toFilePath(): String {
        return groupId.replace(".", File.separator) + File.separator +
                artifactId.replace(".", File.separator) + File.separator +
                version
    }

    fun toDepend(type: BuildType): String {
        when (type) {
            BuildType.MAVEN -> {
                if (version.isNotEmpty() && "null" != version) {
                    return """
                                    <dependency>
                                        <groupId>${groupId}</groupId>
                                        <artifactId>${artifactId}</artifactId>
                                        <version>${version}</version>
                                    </dependency>
                """.trimIndent()
                } else {
                    return """
                                    <dependency>
                                        <groupId>${groupId}</groupId>
                                        <artifactId>${artifactId}</artifactId>
                                    </dependency>
                """.trimIndent()
                }
            }

            BuildType.GRADLE -> {
//                return "implementation(\"${groupId}:${artifactId}:${version}\")"
                return if (version.isNotEmpty() && "null" != version) {
                    "${groupId}:${artifactId}:${version}"
                } else {
                    "${groupId}:${artifactId}"
                }
            }

        }
    }
}
