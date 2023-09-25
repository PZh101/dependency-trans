package com.github.dt.dependencytrans.parse

import com.github.dt.dependencytrans.BuildType
import com.github.dt.dependencytrans.model.DependModel

/**
 * 字符串分析
 * @author zhoup
 * @since 2023/9/23
 */
object SelectedStringParses {
    fun toDependModel(selected: String?): DependModel {
        if (selected is String) {
            val trimmedSelectedText = selected.trim()
            val result: DependModel = when (guessSelectedType(selected)) {
                BuildType.MAVEN -> {
                    val commentRemoved: StringBuilder = java.lang.StringBuilder()
                    for (line in trimmedSelectedText.split("\n")) {
                        val trimmedLine = line.trim()
                        if (trimmedLine.startsWith("<!--") && trimmedLine.endsWith("-->")) {
                            continue
                        }
                        commentRemoved.append(line)
                    }
                    val validSelected = commentRemoved.toString()
                    val groupId =
                        Regex("(?<=\\<groupId\\>)(.+)(?=\\<\\/groupId\\>)").find(
                            validSelected,
                            0
                        )?.value.toString()
                    val artifactId =
                        Regex("(?<=\\<artifactId\\>)(.+)(?=\\<\\/artifactId\\>)").find(
                            validSelected,
                            0
                        )?.value.toString()
                    val version =
                        Regex("(?<=\\<version\\>)(.+)(?=\\<\\/version\\>)").find(
                            validSelected,
                            0
                        )?.value.toString()
                    DependModel(groupId, artifactId, version)
                }

                BuildType.GRADLE -> {
                    val collected = trimmedSelectedText.split(":", ignoreCase = false, limit = 3)
                    if (collected.size == 2) {
                        DependModel(collected[0], collected[1], "")
                    } else {
                        DependModel(collected[0], collected[1], collected[2])
                    }
                }
            }
            return result
        }
        return DependModel("", "", "")
    }

    private fun guessSelectedType(selected: String): BuildType {
        val trimmedSelectedText = selected.trim()
        val matches = trimmedSelectedText.matches(Regex("^(\\<dependency\\>)((.|\\s)+)(\\<\\/dependency\\>)\$"))
        if (matches) {
            return BuildType.MAVEN
        } else {
            val colonCount = trimmedSelectedText.count { it == ':' }
            if (colonCount in 1..2) {
                return BuildType.GRADLE
            }
        }
        val s = "无法猜测选中文本( $selected )的类型"
//        SwingUtilities.invokeLater { Messages.showMessageDialog(s, "ERROR", UIUtil.getErrorIcon()) }
        throw UnsupportedOperationException(s)
    }

//    class Node(val nodeName: String, nodeValue: String, children: List<Node>)
//
//    fun simpleResolveXml(xmlString: String): Node {
//        val trimmedXmlString = xmlString.trim()
//        val nodeList = ArrayList<Node>()
//        val root = Node("root", "", nodeList)
//        val indices = trimmedXmlString.indices
//        val len = trimmedXmlString.length - 1
//        for (idx in indices) {
//            val ch = trimmedXmlString[idx]
//            if (ch == '<') {
//                while (idx < len) {
//                    idx.dec()
//                    val ch1 = trimmedXmlString[idx]
//                }
//            }
//        }
//        return root
//    }
}