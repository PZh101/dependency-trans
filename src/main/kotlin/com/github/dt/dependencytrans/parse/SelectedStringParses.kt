package com.github.dt.dependencytrans.parse

import com.github.dt.dependencytrans.BuildType
import com.github.dt.dependencytrans.model.DependModel
import com.intellij.openapi.ui.Messages
import com.intellij.util.ui.UIUtil
import javax.swing.SwingUtilities

/**
 * 字符串分析
 * @author zhoup
 * @since 2023/9/23
 */
object SelectedStringParses {
    fun toDependModel(selected: String?): DependModel {
        if (selected is String) {
            val result: DependModel = when (guessSelectedType(selected)) {
                BuildType.MAVEN -> {
                    val groupId = Regex("(?<=\\<groupId\\>)(.+)(?=\\<\\/groupId\\>)").find(selected, 0)?.value.toString()
                    val artifactId = Regex("(?<=\\<artifactId\\>)(.+)(?=\\<\\/artifactId\\>)").find(selected, 0)?.value.toString()
                    val version = Regex("(?<=\\<version\\>)(.+)(?=\\<\\/version\\>)").find(selected, 0)?.value.toString()
                    DependModel(groupId, artifactId, version)
                }

                BuildType.GRADLE -> {
                    val collected = selected.split(":", ignoreCase = false, limit = 3)
                    DependModel(collected[0], collected[1], collected[2])
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
            if (colonCount in 2..3) {
                return BuildType.GRADLE
            }
        }
        val s = "无法猜测选中文本( $selected )的类型"
        SwingUtilities.invokeLater { Messages.showMessageDialog(s, "ERROR", UIUtil.getErrorIcon()) }
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