package gg.jrg.audiminder.core.presentation

interface BindableView {
    val title: String
    val subtitle: String

    fun getImageFilePaths(): List<String>
}