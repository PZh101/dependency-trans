package com.github.dt.dependencytrans.parse

import org.junit.jupiter.api.Test

/**
 * @author zhoup
 * @since 2023/9/23
 */
class SelectedStringParsesTest {

    @Test
    fun toDependModel() {
        val s1 = """
          <dependency>
    <groupId>org.apache</groupId>
    <artifactId>common-io</artifactId>
    <version>1.0.2</version>
</dependency>  
        """.trimIndent()
        println(SelectedStringParses.toDependModel(s1))
        val s2 = "org.apache:common-io:1.0.2"
        println(SelectedStringParses.toDependModel(s2))
    }
}