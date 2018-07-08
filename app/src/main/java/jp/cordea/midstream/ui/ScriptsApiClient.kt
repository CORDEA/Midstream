package jp.cordea.midstream.ui

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.script.Script
import com.google.api.services.script.model.ExecutionRequest
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.cordea.midstream.BuildConfig
import jp.cordea.midstream.CredentialProvider
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScriptsApiClient @Inject constructor(provider: CredentialProvider) {
    companion object {
        private const val GET = "get"
        private const val APPEND = "appendWithJson"
    }

    private val service = Script.Builder(
            AndroidHttp.newCompatibleTransport(),
            JacksonFactory.getDefaultInstance(),
            HttpRequestInitializer {
                provider.get().initialize(it)
                it.readTimeout = 380000
            })
            .build()

    @Suppress("UNCHECKED_CAST")
    fun get(): Single<List<List<BigDecimal>>> {
        val request = ExecutionRequest()
                .setFunction(GET)
                .setParameters(listOf(BuildConfig.SHEET_ID))
        return Single
                .create<List<List<BigDecimal>>> { emitter ->
                    val op = service.scripts().run(BuildConfig.SCRIPT_ID, request).execute()
                    if (op.error != null) {
                        emitter.onError(IllegalStateException(op.error.message))
                        return@create
                    }
                    val result = op.response?.get("result")
                    if (result != null) {
                        emitter.onSuccess(result as List<List<BigDecimal>>)
                        return@create
                    }
                    emitter.onError(IllegalStateException())
                }
                .subscribeOn(Schedulers.io())
    }

    fun append(vararg args: String): Completable {
        val json = JSON.stringify(StringSerializer.list, args.toList())
        val request = ExecutionRequest()
                .setFunction(APPEND)
                .setParameters(listOf(BuildConfig.SHEET_ID, json))
        return Completable
                .create { emitter ->
                    val op = service.scripts().run(BuildConfig.SCRIPT_ID, request).execute()
                    if (op.error != null) {
                        emitter.onError(IllegalStateException(op.error.message))
                        return@create
                    }
                    emitter.onComplete()
                }
                .subscribeOn(Schedulers.io())
    }
}
