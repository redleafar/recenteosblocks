package one.block.recenteosblocks.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import one.block.recenteosblocks.util.Event

class HomeViewModel : ViewModel() {

    private val _goToListEvent = MutableLiveData<Event<Int>>()

    val goToListEvent : LiveData<Event<Int>>
        get() = _goToListEvent

    fun goToList(view: View) {
        _goToListEvent.value = Event(view.id)
    }
}