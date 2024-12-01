import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object Notifier {
    private const val GENERAL_GROUP_ID = "git.commit.genie.notification.general"
    fun notifyError(project: Project, content: String) {
        innerNotify(project, content, NotificationType.ERROR)
    }

    fun notifyInfo(project: Project, content: String) {
        innerNotify(project, content, NotificationType.INFORMATION)
    }

    fun notifyWarning(project: Project, content: String) {
        innerNotify(project, content, NotificationType.WARNING)
    }

    private fun innerNotify(project: Project, content: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(GENERAL_GROUP_ID)
                .createNotification(content, type)
                .notify(project)
    }
}

