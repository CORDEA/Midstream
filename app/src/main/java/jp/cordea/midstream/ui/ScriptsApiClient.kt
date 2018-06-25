package jp.cordea.midstream.ui

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.script.Script
import com.google.api.services.script.model.ExecutionRequest
import io.reactivex.Single
import jp.cordea.midstream.BuildConfig
import jp.cordea.midstream.CredentialProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScriptsApiClient @Inject constructor(provider: CredentialProvider) {
    companion object {
        private const val GET = "get"
        private const val APPEND = "append"
    }

    private val service = Script.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            HttpRequestInitializer {
                provider.get().initialize(it)
                it.readTimeout = 380000
            })
            .build()

    fun get() = request(GET, BuildConfig.SHEET_ID)

    fun append(vararg args: Any) = request(APPEND, BuildConfig.SHEET_ID, args.toList())

    @Suppress("UNCHECKED_CAST")
    private fun request(name: String, vararg args: Any): Single<Map<String, String>> {
        val request = ExecutionRequest().setFunction(name).setParameters(args.toList())
        return Single.create<Map<String, String>> { emitter ->
            val op = service.scripts().run(BuildConfig.SCRIPT_ID, request).execute()
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
