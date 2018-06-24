package jp.cordea.midstream.ui.login

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import jp.cordea.midstream.CredentialProvider
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class Authenticator @Inject constructor(
        provider: CredentialProvider,
        private val context: Context
) : EasyPermissions.PermissionCallbacks {
    companion object {
        private const val REQUEST_PERMISSION_GET_ACCOUNTS = 1
        private const val REQUEST_ACCOUNT_PICKER = 2
    }

    interface AuthenticatorCallbacks {
        fun requestStartActivity(requestCode: Int, intent: Intent)
        fun requestStartMainActivity()
        fun requestGetAccountsPermission(requestCode: Int)
    }

    private val credential = provider.get()
    private val keyManager = KeyManager(context)

    lateinit var callbacks: AuthenticatorCallbacks

    fun authIfNeeded() {
        if (credential.selectedAccountName == null) {
            auth()
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private fun auth() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.GET_ACCOUNTS)) {
            val name = keyManager.accountName
            if (name.isEmpty()) {
                callbacks.requestStartActivity(
                        REQUEST_ACCOUNT_PICKER,
                        credential.newChooseAccountIntent()
                )
            } else {
                credential.selectedAccountName = name
            }
            return
        }
        callbacks.requestGetAccountsPermission(REQUEST_PERMISSION_GET_ACCOUNTS)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != REQUEST_ACCOUNT_PICKER) {
            return
        }
        if (resultCode != Activity.RESULT_OK || data?.extras == null) {
            return
        }
        val name = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        if (name.isNullOrEmpty()) {
            return
        }
        keyManager.accountName = name
        credential.selectedAccountName = name
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        callbacks.requestStartMainActivity()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
    }
}
