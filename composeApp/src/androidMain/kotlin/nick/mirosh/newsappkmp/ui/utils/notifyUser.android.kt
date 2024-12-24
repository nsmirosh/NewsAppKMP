package nick.mirosh.newsappkmp.ui.utils

import android.widget.Toast


actual fun showError(message: String) {
    Toast.makeText(applicationContext(), message, Toast.LENGTH_SHORT).show()
}