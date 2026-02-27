package org.cheek.secretkeeper

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.JComponent
import javax.swing.JPanel
import java.awt.CardLayout
import java.beans.PropertyChangeListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.editor.EditorFactory

class FileEditor(project: Project, private val file: VirtualFile) : UserDataHolderBase(), FileEditor {

    private val panel = JPanel(CardLayout())
    private val blurPanel = BlurPanel()
    private val editor: Editor
    // Associate the document with the file using FileDocumentManager
    private val document: Document = FileDocumentManager.getInstance().getDocument(file)
        ?: EditorFactory.getInstance().createDocument(String(file.contentsToByteArray(), Charsets.UTF_8).replace("\r\n", "\n"))

    init {

        editor = EditorFactory.getInstance().createEditor(document, project, file, false)

        // Set up UI with CardLayout for blurring/unblurring
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

    // Use IDE’s isDocumentUnsaved to check if the document has changes
    override fun isModified(): Boolean = FileDocumentManager.getInstance().isDocumentUnsaved(document)

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {
        // Release editor on disposal
        EditorFactory.getInstance().releaseEditor(editor)
    }

    override fun getFile(): VirtualFile = file
}
