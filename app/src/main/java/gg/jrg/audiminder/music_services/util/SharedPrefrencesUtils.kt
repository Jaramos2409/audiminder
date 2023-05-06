package gg.jrg.audiminder.music_services.util

import android.content.Context
import java.io.File

fun Context.clearSharedPreferences() {
    val dir = File("${this.filesDir.parent}/shared_prefs/")
    val children: Array<String> = dir.list() as Array<String>
    for (i in children.indices) {
        this.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit()
            .clear().apply()
        File(dir, children[i]).delete()
    }
}