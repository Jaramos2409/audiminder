package gg.jrg.audiminder.core.domain

import gg.jrg.audiminder.core.data.dto.UserDetailsDTO
import gg.jrg.audiminder.core.data.source.UserDetailsKey

data class UserDetails(
    val key: UserDetailsKey,
    val value: String
) : DTOMappable<UserDetailsDTO> {
    override fun asDatabaseModel(): UserDetailsDTO = UserDetailsDTO(
        key = this.key,
        value = this.value
    )
}
