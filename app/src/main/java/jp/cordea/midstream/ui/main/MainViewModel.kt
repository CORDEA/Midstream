package jp.cordea.midstream.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: MainRepository

    private val compositeDisposable = CompositeDisposable()
    private val mutableNotifyStateChanged = MutableLiveData<MainListState>()
    val notifyStateChanged: LiveData<MainListState> = mutableNotifyStateChanged

    fun fetch() = repository
            .getBusData()
            .subscribe({
                mutableNotifyStateChanged.postValue(MainListState.BusDataChanged(it))
            }, {
                if (it is UserRecoverableAuthIOException) {
                    mutableNotifyStateChanged.postValue(MainListState.AuthRequired(it.intent))
                } else {
                    mutableNotifyStateChanged.postValue(MainListState.ErrorOccurred(it))
                }
            })
            .addTo(compositeDisposable)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
