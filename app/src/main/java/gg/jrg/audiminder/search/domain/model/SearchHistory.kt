package gg.jrg.audiminder.search.domain.model

import gg.jrg.audiminder.core.domain.DTOMappable
import gg.jrg.audiminder.search.data.dto.SearchHistoryDTO
import java.util.*

data class SearchHistory(
    val searchId: Int,
    val query: String,
    val timestamp: Date
) : DTOMappable<SearchHistoryDTO> {
    override fun asDatabaseModel(): SearchHistoryDTO = SearchHistoryDTO(
        searchId = this.searchId,
        query = this.query,
        timestamp = this.timestamp
    )
}