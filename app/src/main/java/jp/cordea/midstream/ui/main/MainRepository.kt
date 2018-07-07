package jp.cordea.midstream.ui.main

import io.reactivex.Completable
import io.reactivex.Single
import jp.cordea.midstream.BusData
import jp.cordea.midstream.BusType
import jp.cordea.midstream.ui.ScriptsApiClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
        private val apiClient: ScriptsApiClient
) {
    fun getBusData(): Single<List<BusData>> = apiClient
            .get()
            .map {
                it.filter {
                    it.size == 3
                }.map {
                    BusData(it[0].toLong(), it[1].toLong(), BusType.from(it[2].toInt()))
                }
            }

    fun appendBusData(data: BusData): Completable = apiClient
            .append(
                    data.departureTime.toString(),
                    data.arrivalTime.toString(),
                    data.busType.code.toString()
            )
}
