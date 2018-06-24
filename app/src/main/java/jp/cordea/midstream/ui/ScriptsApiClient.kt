package jp.cordea.midstream.ui

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.script.Script
import com.google.api.services.script.model.ExecutionRequest
import io.reactivex.Single

class ScriptsApiClient(credential: GoogleAccountCredential) {
    companion object {
        private const val ID = ""
    }

    private val service = Script.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            HttpRequestInitializer {
                credential.initialize(it)
                it.readTimeout = 380000
            })
            .build()

    @Suppress("UNCHECKED_CAST")
    private fun request(): Single<Map<String, String>> {
        val request = ExecutionRequest().setFunction("")
        return Single.create<Map<String, String>> { emitter ->
            val op = service.scripts().run(ID, request).execute()
            if (op.error != null) {
                emitter.onError(IllegalStateException(op.error.message))
                return@create
            }
            val result = op.response?.get("result")
            if (result != null) {
                emitter.onSuccess(result as Map<String, String>)
                return@create
            }
            emitter.onError(IllegalStateException())
        }
    }
}