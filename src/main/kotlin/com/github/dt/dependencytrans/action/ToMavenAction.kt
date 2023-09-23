package com.github.dt.dependencytrans.action

import com.github.dt.dependencytrans.BuildType

/**
 * 转为maven依赖
 * @author zhoup
 * @since 2023/9/21
 */
class ToMavenAction : MyEditorAction(MyEditorActionHandler(BuildType.MAVEN)) {
}