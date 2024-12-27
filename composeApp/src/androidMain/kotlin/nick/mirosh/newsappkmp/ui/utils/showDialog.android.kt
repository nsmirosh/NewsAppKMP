package nick.mirosh.newsappkmp.ui.utils
// In the Android-specific code (androidMain)
import android.app.AlertDialog
import android.content.Context


class AndroidDialogProvider(
    private val context: Context
) : DialogProvider {
    override fun showDialog(title: String, message: String, onDismiss: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ -> onDismiss() }
        builder.show()
    }
}