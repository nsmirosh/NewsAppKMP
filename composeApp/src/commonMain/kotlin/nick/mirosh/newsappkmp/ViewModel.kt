package nick.mirosh.newsappkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class MyViewModel(val permissionsController: PermissionsController): ViewModel() {
    fun onPhotoPressed() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.COARSE_LOCATION)
                // Permission has been granted successfully.
            } catch(deniedAlways: DeniedAlwaysException) {
                // Permission is always denied.
            } catch(denied: DeniedException) {
                // Permission was denied.
            }
        }
    }
}