package jp.cordea.midstream

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.midstream.ui.login.LoginFragmentModule

@Module
interface MainActivityModule {
    @ContributesAndroidInjector(modules = [
        LoginFragmentModule::class
    ])
    fun contributeMainActivity(): MainActivity
}
