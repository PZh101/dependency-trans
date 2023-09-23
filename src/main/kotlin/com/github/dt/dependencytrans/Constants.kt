package com.github.dt.dependencytrans

/**
 * @author zhoup
 * @since 2023/9/21
 */
object Constants {
    const val Author = "zhouPan"
    const val mavenTemplate="""
                <dependency>
                    <groupId>{groupId}</groupId>
                    <artifactId>{artifactId}</artifactId>
                    <version>{version}</version>
                </dependency>
    """
}