package jp.cordea.midstream.ui.login

import androidx.lifecycle.ViewModelStoreOwner
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.midstream.ViewModelModule

@Module
interface LoginFragmentModule {
    @ContributesAndroidInjector(modules = [
        LoginFragmentBindModule::class,
        LoginViewModelModule::class
    ])
    fun contributeLoginFragment(): LoginFragment
}

@Module
interface LoginFragmentBindModule {
    @Binds
    fun bindAuthenticatorCallbacks(fragment: LoginFragment): Authenticator.AuthenticatorCallbacks

    @Binds
    fun bindViewModelStoreOwner(fragment: LoginFragment): ViewModelStoreOwner
}

@Module
class LoginViewModelModule : ViewModelModule<LoginViewModel>(LoginViewModel::class)
