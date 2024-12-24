package nick.mirosh.newsappkmp.ui.utils

import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

actual fun showError(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        "Error",
        message,
        UIAlertControllerStyleAlert
    )
    alert.addAction(
        UIAlertAction.actionWithTitle(
        "OK",
        UIAlertActionStyleDefault,
        null
    ))
    val controller = UIApplication.sharedApplication.keyWindow?.rootViewController
    controller?.presentViewController(alert, animated = true, completion = null)
}