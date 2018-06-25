package jp.cordea.midstream.ui.login

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
}

@Module
class LoginViewModelModule : ViewModelModule<LoginViewModel>(LoginViewModel::class)
