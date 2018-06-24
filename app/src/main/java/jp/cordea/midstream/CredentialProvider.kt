package jp.cordea.midstream

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialProvider @Inject constructor(context: Context) {
    companion object {
        private val SCOPES = listOf("https://www.googleapis.com/auth/script.projects")
    }

    private val credential = GoogleAccountCredential.usingOAuth2(context, SCOPES)
            .setBackOff(ExponentialBackOff())

    fun get(): GoogleAccountCredential = credential
}
