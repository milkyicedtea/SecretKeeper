package org.cheek.secretkeeper

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel
import com.intellij.openapi.editor.EditorFactory
import java.awt.CardLayout

class FileEditor(private val project: Project, private val file: VirtualFile) : UserDataHolderBase(),
    FileEditor {
    private val panel = JPanel(CardLayout())
    private val blurPanel = BlurPanel()
    private val editor: Editor

    init {
        val content = String(file.contentsToByteArray(), Charsets.UTF_8).replace("\r\n", "\n")
        val document = EditorFactory.getInstance().createDocument(content)
        editor = EditorFactory.getInstance().createEditor(document, project, file, false)

        // Set up UI with CardLayout
        panel.add(blurPanel, "BLURRED")
        panel.add(editor.component, "UNBLURRED")
        (panel.layout as CardLayout).show(panel, "BLURRED")
    }

    fun unblur() {
        (panel.layout as CardLayout).show(panel, "UNBLURRED")
        panel.revalidate()
        panel.repaint()
    }

    override fun getComponent(): JComponent = panel

    override fun getPreferredFocusedComponent(): JComponent = editor.contentComponent

    override fun getName(): String = "Secret File Editor"

    override fun setState(state: FileEditorState) {}

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {
        EditorFactory.getInstance().releaseEditor(editor)
    }

    override fun getFile(): VirtualFile = file
}