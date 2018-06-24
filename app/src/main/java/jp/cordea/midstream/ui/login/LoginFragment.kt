package jp.cordea.midstream.ui.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import jp.cordea.midstream.R
import pub.devrel.easypermissions.EasyPermissions

class LoginFragment : Fragment(), Authenticator.AuthenticatorCallbacks {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private val authenticator by lazy {
        Authenticator(context!!).also {
            it.callbacks = this
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        authenticator.authIfNeeded()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authenticator.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        authenticator.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        )
    }

    override fun requestGetAccountsPermission(requestCode: Int) {
        EasyPermissions.requestPermissions(
                this,
                "",
                requestCode,
                Manifest.permission.GET_ACCOUNTS
        )
    }

    override fun requestStartActivity(requestCode: Int, intent: Intent) {
        startActivityForResult(
                intent,
                requestCode
        )
    }

    override fun requestStartMainActivity() {
        Navigation.findNavController(view!!).navigate(R.id.action_loginFragment_to_mainFragment)
    }
}
