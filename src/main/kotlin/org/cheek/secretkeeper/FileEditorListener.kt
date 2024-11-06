package org.cheek.secretkeeper

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ConcurrentHashMap

class FileEditorListener : FileEditorManagerListener {
    // track files that have already shown a warning dialog in the session
    private val warnedFiles = ConcurrentHashMap<VirtualFile, Boolean>()
    private val dialogOpenFiles = ConcurrentHashMap<VirtualFile, Boolean>()
    private var debounceTimer: Timer? = null

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        // log the file opening for debugging
        // println("File opened: ${file.name}, isSensitive: ${isSensitiveFile(file)}")
//        Thread.dumpStack()

        if (isSensitiveFile(file) && warnedFiles.putIfAbsent(file, true) == null) {
            debounceTimer?.cancel() // cancel any existing timer

            debounceTimer = Timer().apply {
                schedule(object : TimerTask() {
                    override fun run() {
                        ApplicationManager.getApplication().invokeLater {
                            if (dialogOpenFiles.putIfAbsent(file, true) == null) {
                                showWarningDialog(source.project, file) {
                                    dialogOpenFiles.remove(file)
                                }
                            }
                        }
                    }
                }, 300) // set debounce delay
            }
        }
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        // reset the warning state for the file when closed
        warnedFiles.remove(file)
    }

    private fun showWarningDialog(project: Project, file: VirtualFile, onDialogClosed: () -> Unit) {
        // Use invokeLater to show dialog asynchronously
        ApplicationManager.getApplication().invokeLater {
            val dialog = FileWarningDialog(project, file)
            if (dialog.showAndGet()) {
                // User confirmed, unblur editor
                val editor = FileEditorManager.getInstance(project).getSelectedEditor(file)
                if (editor is FileEditor) {
                    editor.unblur()
                }
            } else {
                // User canceled, close the file
                FileEditorManager.getInstance(project).closeFile(file)
            }
            // Call the callback to indicate the dialog has closed
            onDialogClosed()
        }
    }
}

fun isSensitiveFile(file: VirtualFile): Boolean {
    return file.name.contains(".env") || file.extension == "key"
}