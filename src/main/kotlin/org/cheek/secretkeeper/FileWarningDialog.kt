package org.cheek.secretkeeper

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class FileWarningDialog(project: Project, private val file: VirtualFile) : DialogWrapper(project) {
    init {
        title = "Sensitive File Warning"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("The file '${file.name}' contains sensitive information.")
            }
            row {
                label("If you are sharing your screen, you should not enter.")
            }
            row {
                label("Are you sure you want to open it?")
            }
        }
    }

    override fun doCancelAction() {
        super.doCancelAction()
        close(CANCEL_EXIT_CODE)
    }
}