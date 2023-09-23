package com.github.dt.dependencytrans.action

import com.github.dt.dependencytrans.BuildType

/**
 * @author zhoup
 * @since 2023/9/21
 */
class ToGradleAction : MyEditorAction(MyEditorActionHandler(BuildType.GRADLE)) {
}