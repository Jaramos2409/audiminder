package gg.jrg.audiminder.core.domain

interface DTOMappable<T> {
    fun asDatabaseModel(): T
}

fun <T, R : DTOMappable<T>> List<R>.asDatabaseModelList(): List<T> {
    return map { it.asDatabaseModel() }
}