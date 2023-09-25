package com.github.dt.dependencytrans.action

import com.github.dt.dependencytrans.BuildType
import com.github.dt.dependencytrans.model.MyStringTransferable
import com.github.dt.dependencytrans.parse.SelectedStringParses
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.ui.Messages
import javax.swing.SwingUtilities

/**
 * @author zhoup
 * @since 2023/9/23
 */
class MyEditorActionHandler(private val toType: BuildType) : EditorActionHandler() {
    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
//        super.doExecute(editor, caret, dataContext)
        val runnable = Runnable {
            try {
//                executeWriteAction(editor, dataContext, additionalParameter.second)
//                editor
//                val selectedText = caret?.selectedText
//                var data = dataContext?.getData(CommonDataKeys.PROJECT)
                val selectionModel = editor.selectionModel
                val selectedText1 = selectionModel.selectedText
                val toDepend = SelectedStringParses.toDependModel(selectedText1).toDepend(toType)
                // result will write in clipboard
                CopyPasteManager.getInstance().setContents(MyStringTransferable(toDepend))
                editor.document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, toDepend)
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