package com.github.dt.dependencytrans.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.impl.EditorComponentImpl
import com.intellij.openapi.editor.textarea.TextComponentEditorImpl
import com.intellij.ui.SpeedSearchBase
import com.intellij.ui.speedSearch.SpeedSearchSupply
import javax.swing.JComponent
import javax.swing.JTextField
import javax.swing.text.JTextComponent

/**
 * @author zhoup
 * @since 2023/9/23
 */
open class MyEditorAction(defaultHandler: EditorActionHandler) : EditorAction(defaultHandler) {

    override fun getEditor(dataContext: DataContext): Editor? {
        return getEditorFromContext(dataContext)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
//        return super.getActionUpdateThread()

        //https://github.com/krasa/StringManipulation/issues/182
        //Access is allowed from event dispatch thread only exception is thrown from MyEditorAction.findActiveSpeedSearchTextField in IntelliJ 2022.3
        return ActionUpdateThread.EDT
    }

    private fun getEditorFromContext(dataContext: DataContext): Editor? {
        val editor = CommonDataKeys.EDITOR.getData(dataContext)
        if (editor != null) return editor
        val project = CommonDataKeys.PROJECT.getData(dataContext)
        val data: Any? = PlatformCoreDataKeys.CONTEXT_COMPONENT.getData(dataContext)
        if (data is EditorComponentImpl) {
            // can happen if editor is already disposed, or if it's in renderer mode
            return null
        }
        if (data is JTextComponent) {
            return TextComponentEditorImpl(project, (data as JTextComponent?)!!)
        }
        if (data is JComponent) {
            val findActiveSpeedSearchTextField = (data as JComponent?)?.let { findActiveSpeedSearchTextField(it) }
            if (findActiveSpeedSearchTextField is JTextField) {
                val field: JTextField = findActiveSpeedSearchTextField
                return TextComponentEditorImpl(project, field)
            }
        }
        return null
    }

    private fun findActiveSpeedSearchTextField(c: JComponent): JTextField? {
        val supply = SpeedSearchSupply.getSupply(c)
        if (supply is SpeedSearchBase<*>) {
            return supply.searchField
        }
        if (c is DataProvider) {
            val component = PlatformDataKeys.SPEED_SEARCH_COMPONENT.getData((c as DataProvider))
            if (component is JTextField) {
                return component
            }
        }
        return null
    }
}