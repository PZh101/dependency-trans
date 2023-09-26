package com.github.dt.dependencytrans.action

import com.github.dt.dependencytrans.model.MyStringTransferable
import com.github.dt.dependencytrans.parse.SelectedStringParses
import com.intellij.application.options.PathMacrosImpl
import com.intellij.application.options.PathMacrosImpl.Companion.MAVEN_REPOSITORY
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.ui.Messages
import com.intellij.util.ui.UIUtil
import javax.swing.SwingUtilities


/**
 * @author zhoup
 * @since 2023/9/23
 */
class MyEditorPopupActionHandler : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
//        super.doExecute(editor, caret, dataContext)
        val runnable = Runnable {
            try {
//                executeWriteAction(editor, dataContext, additionalParameter.second)
//                editor
//                val selectedText = caret?.selectedText
                val selectionModel = editor.selectionModel
                val selectedText1 = selectionModel.selectedText
                val mavenRepository = PathMacrosImpl.getInstanceEx().getValue(MAVEN_REPOSITORY)
                val filePath = SelectedStringParses.toDependModel(selectedText1).toFilePath(mavenRepository)
//                val project = dataContext?.getData(CommonDataKeys.PROJECT)
//                val pathMacroManager = project?.getService(PathMacroManager::class.java)
                //写入到剪切板
                CopyPasteManager.getInstance().setContents(MyStringTransferable(filePath))
                SwingUtilities.invokeLater {
                    Messages.showMessageDialog(
                        filePath,
                        "RelativePath",
                        UIUtil.getInformationIcon()
                    )
                }
            } catch (e: Exception) {
                SwingUtilities.invokeLater { Messages.showErrorDialog(editor.project, e.message, "Error") }
            }
        }

        object : EditorWriteActionHandler(false) {
            override fun executeWriteAction(editor1: Editor, caret1: Caret?, dataContext1: DataContext) {
                runnable.run()
            }
        }.doExecute(editor, caret, dataContext)
    }
}