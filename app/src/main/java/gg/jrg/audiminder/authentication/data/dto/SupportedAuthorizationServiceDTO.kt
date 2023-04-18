package gg.jrg.audiminder.authentication.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.authentication.domain.model.SupportedAuthorizationService
import gg.jrg.audiminder.core.data.DomainMappable

@Entity(tableName = "supported_auth_services")
data class SupportedAuthorizationServiceDTO(
    @PrimaryKey(autoGenerate = true) val serviceId: Int,
    val serviceName: String
) : DomainMappable<SupportedAuthorizationService> {
    override fun asDomainModel(): SupportedAuthorizationService = SupportedAuthorizationService(
        serviceId = this.serviceId,
        serviceName = this.serviceName
    )
}
