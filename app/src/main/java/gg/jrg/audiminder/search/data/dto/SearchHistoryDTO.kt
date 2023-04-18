package gg.jrg.audiminder.search.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.search.domain.model.SearchHistory
import java.util.*

@Entity(tableName = "search_history")
data class SearchHistoryDTO(
    @PrimaryKey(autoGenerate = true) val searchId: Int,
    val query: String,
    val timestamp: Date
) : DomainMappable<SearchHistory> {
    override fun asDomainModel(): SearchHistory = SearchHistory(
        searchId = this.searchId,
        query = this.query,
        timestamp = this.timestamp
    )
}
