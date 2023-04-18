package gg.jrg.audiminder.authentication.domain.model

import gg.jrg.audiminder.authentication.data.dto.SupportedAuthorizationServiceDTO
import gg.jrg.audiminder.core.domain.DTOMappable

data class SupportedAuthorizationService(
    val serviceId: Int,
    val serviceName: String
) : DTOMappable<SupportedAuthorizationServiceDTO> {
    override fun asDatabaseModel(): SupportedAuthorizationServiceDTO =
        SupportedAuthorizationServiceDTO(
            serviceId = this.serviceId,
            serviceName = this.serviceName
        )
}
