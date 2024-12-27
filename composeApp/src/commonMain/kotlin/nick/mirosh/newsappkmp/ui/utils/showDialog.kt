package nick.mirosh.newsappkmp.ui.utils


interface DialogProvider {
    fun showDialog(title: String, message: String, onDismiss: () -> Unit)
}
