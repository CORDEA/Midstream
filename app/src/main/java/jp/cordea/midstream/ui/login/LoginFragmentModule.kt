package jp.cordea.midstream.ui.login

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface LoginFragmentModule {
    @ContributesAndroidInjector(modules = [
        LoginFragmentBindModule::class
    ])
    fun contributeLoginFragment(): LoginFragment
}

@Module
interface LoginFragmentBindModule {
    @Binds
    fun bindAuthenticatorCallbacks(fragment: LoginFragment): Authenticator.AuthenticatorCallbacks
}
