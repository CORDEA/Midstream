package jp.cordea.midstream

import android.app.Application
import android.content.Context
import dagger.Provides

class AppModule {
    @Provides
    fun provideContext(application: Application): Context =
            application.applicationContext
}
