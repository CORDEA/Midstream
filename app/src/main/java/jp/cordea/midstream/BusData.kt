package jp.cordea.midstream

data class BusData(
        val departureTime: Long,
        val arrivalTime: Long,
        val busType: BusType
)

enum class BusType(val code: Int) {
    OLD(0),
    NEW(1),
    NEW_ONE(2);

    companion object {
        fun from(code: Int): BusType = values().first { it.code == code }
    }
}
