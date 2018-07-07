package jp.cordea.midstream.ui.main

import android.content.Intent
import jp.cordea.midstream.BusData

sealed class MainListState {
    class BusDataChanged(val data: List<BusData>) : MainListState()
    class AuthRequired(val intent: Intent) : MainListState()
    class ErrorOccurred(val throwable: Throwable) : MainListState()
}
