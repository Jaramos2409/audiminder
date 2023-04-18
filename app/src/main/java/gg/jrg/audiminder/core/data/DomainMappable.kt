package gg.jrg.audiminder.core.data

interface DomainMappable<T> {
    fun asDomainModel(): T
}

fun <T, R : DomainMappable<T>> List<R>.asDomainModelList(): List<T> {
    return map { it.asDomainModel() }
}
