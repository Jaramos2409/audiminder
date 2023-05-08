package gg.jrg.audiminder.core.data.dto

import androidx.room.Entity
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.core.data.source.UserDetailsKey
import gg.jrg.audiminder.core.domain.UserDetails

@Entity(
    tableName = "user_details",
    primaryKeys = ["key"]
)
data class UserDetailsDTO(
    val key: UserDetailsKey,
    val value: String
) : DomainMappable<UserDetails> {
    override fun asDomainModel(): UserDetails = UserDetails(
        key = this.key,
        value = this.value
    )
}