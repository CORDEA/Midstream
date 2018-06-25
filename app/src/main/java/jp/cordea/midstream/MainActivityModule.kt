package jp.cordea.midstream

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.midstream.ui.login.LoginFragmentModule
import jp.cordea.midstream.ui.main.MainFragmentModule

@Module
interface MainActivityModule {
    @ContributesAndroidInjector(modules = [
        MainFragmentModule::class,
        LoginFragmentModule::class
    ])
    fun contributeMainActivity(): MainActivity
}
