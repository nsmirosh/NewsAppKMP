package nick.mirosh.newsappkmp.ui.utils
import platform.UIKit.*

class IosDialogProvider : DialogProvider {

    override fun showDialog(title: String, message: String, onDismiss: () -> Unit) {
        val alertController = UIAlertController.alertControllerWithTitle(
            title = title,
            message = message,
            preferredStyle = UIAlertControllerStyleAlert
        )

        val action = UIAlertAction.actionWithTitle(
            title = "OK",
            style = UIAlertActionStyleDefault
        ) {
            onDismiss()
        }

        alertController.addAction(action)

        val topController = UIApplication.sharedApplication.keyWindow?.rootViewController
        topController?.presentViewController(alertController, animated = true, completion = null)
    }
}
