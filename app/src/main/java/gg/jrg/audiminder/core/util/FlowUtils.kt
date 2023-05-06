package gg.jrg.audiminder.core.util

import androidx.appcompat.app.AppCompatActivity
import gg.jrg.audiminder.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

fun <T> Flow<T>.logChanges(tag: String = "MutableStateFlow") {
    if (BuildConfig.DEBUG) {
        CoroutineScope(Dispatchers.IO).launch {
            this@logChanges.collect { value ->
                Timber.tag("logChanges: $tag").d("Value changed: $value")
            }
        }
    }
}

class ActivityStateFlowWrapper @Inject constructor(
    val stateFlow: MutableStateFlow<AppCompatActivity?>
)