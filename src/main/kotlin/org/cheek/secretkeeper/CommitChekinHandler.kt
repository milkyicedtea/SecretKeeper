package org.cheek.secretkeeper

import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.changes.CommitContext

class CommitCheckinHandler(private val panel: CheckinProjectPanel) : CheckinHandler() {
    override fun beforeCheckin(): ReturnResult {
        val filesToCommit = panel.virtualFiles // Retrieve files being committed

        // Iterate through each file to check for sensitive data
        for (file in filesToCommit) {
            if (isSensitiveFile(file)) {
                // Show warning and prevent the commit if necessary
                val response = Messages.showYesNoDialog(
                    "The file '${file.name}' contains potential sensitive data. Are you sure you want to commit it?",
                    "Warning: Sensitive Data Detected",
                    Messages.getWarningIcon()
                )

                if (response == Messages.NO) {
                    return ReturnResult.CANCEL // Cancel the commit
                }
            }
        }
        return ReturnResult.COMMIT // Proceed with the commit if no issues
    }
}

class CommitCheckinHandlerFactory : CheckinHandlerFactory() {
    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return CommitCheckinHandler(panel)
    }
}