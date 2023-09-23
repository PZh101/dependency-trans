package com.github.dt.dependencytrans.model

import com.github.dt.dependencytrans.BuildType
import org.junit.jupiter.api.Test

/**
 * @author zhoup
 * @since 2023/9/21
 */
class DependModelTest {

    @Test
    fun toDepend() {
        val dependModel = DependModel("org.apache", "common-io", "1.0.2")
        println(dependModel.toDepend(BuildType.MAVEN))
        println(dependModel.toDepend(BuildType.GRADLE))
        println(dependModel.toFilePath())
    }

    @Test
    fun xmlParse() {
        val s = """
            <dependency>
                <groupId>org.apache</groupId>
                <artifactId>common-io</artifactId>
                <version>1.0.2</version>
            </dependency>
        """.trimIndent()
//        val replace = s.replace(Regex("(<dependency>|</dependency>)"), "")
        val groupId = Regex("(?<=\\<groupId\\>)(.+)(?=\\<\\/groupId\\>)").find(s, 0)?.value.toString()
        val artifactId = Regex("(?<=\\<artifactId\\>)(.+)(?=\\<\\/artifactId\\>)").find(s, 0)?.value.toString()
        val version = Regex("(?<=\\<version\\>)(.+)(?=\\<\\/version\\>)").find(s, 0)?.value.toString()
        println(DependModel(groupId, artifactId, version))
//        XmlUtil.processXmlElements()
//        val saxParser = SAXParserFactory.newInstance().newSAXParser()
//        val inputSource = InputSource()
//        val byteArrayInputStream = ByteArrayInputStream(java.lang.String(s).bytes)
//        inputSource.byteStream = byteArrayInputStream
//        inputSource.characterStream = InputStreamReader(byteArrayInputStream)
//        inputSource.encoding = "utf-8"

//        val sax2Dom = Sax2Dom()
//        saxParser.parse(byteArrayInputStream, sax2Dom)
//        println(sax2Dom.dom.nodeName)
//        XmlUtil.processXmlElements(xml,)
//        val xmlDocumentParser = XMLDocumentParser()
//        val newSAXParser = SAXParserFactory.newDefaultInstance().newSAXParser()
//        val defaultHandler = DefaultHandler()
//        newSAXParser.parse(s, defaultHandler)
//        defaultHandler.startDocument()
    }
}