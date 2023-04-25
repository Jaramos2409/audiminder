package gg.jrg.audiminder.core.util

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

fun <T> MutableStateFlow<T>.logChanges(tag: String = "MutableStateFlow") {
    CoroutineScope(Dispatchers.IO).launch {
        this@logChanges.collect { value ->
            Timber.tag(tag).d("Value changed: $value")
        }
    }
}

class ActivityStateFlowWrapper @Inject constructor(
    val stateFlow: MutableStateFlow<AppCompatActivity?>
)