package jp.cordea.midstream.ui.login

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface LoginFragmentModule {
    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment
}
