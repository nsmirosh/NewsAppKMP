package nick.mirosh.newsappkmp.data.manager

import co.touchlab.kermit.Logger
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PermissionManager(private val permissionsController: PermissionsController) {

    suspend fun requestLocationPermissions(
        scope: CoroutineScope,
        onSuccess: suspend () -> Unit,
        onDenied: () -> Unit,
        onAlwaysDenied: () -> Unit
    ) {
        //TODO in this app we're assuming that the user grants the permission
        // but in a real app you should handle the permission denial with
        // a proper UI/UX - more info https://developer.android.com/training/permissions/requesting

        if (permissionsController.getPermissionState(Permission.COARSE_LOCATION) == PermissionState.Granted) {
            onSuccess()
            return
        }

        //TODO: Hack to overcome the moko libraries' bug for ios first-time permission request
        scope.launch {
            while (permissionsController.getPermissionState(Permission.COARSE_LOCATION) != PermissionState.Granted) {
                delay(200)
            }
            onSuccess()
        }

        try {
            permissionsController.providePermission(Permission.COARSE_LOCATION)
        }
        catch (e: DeniedAlwaysException)  {
            Logger.e("Location permissions denied always")
//            onAlwaysDenied()
        }
        catch (e: Exception) {
            Logger.e("Error requesting location permissions: ${e.message}")
            onDenied()
        }
    }

}
