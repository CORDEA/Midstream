package jp.cordea.midstream.ui.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jp.cordea.midstream.ViewModelModule

@Module
interface MainFragmentModule {
    @ContributesAndroidInjector(modules = [
        MainViewModelModule::class
    ])
    fun contributeMainFragment(): MainFragment
}

@Module
class MainViewModelModule : ViewModelModule<MainViewModel>(MainViewModel::class)
