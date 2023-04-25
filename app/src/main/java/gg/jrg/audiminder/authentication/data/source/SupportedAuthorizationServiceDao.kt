package gg.jrg.audiminder.authentication.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gg.jrg.audiminder.authentication.data.dto.SupportedAuthorizationServiceDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportedAuthorizationServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg supportedAuthorizationServiceDTO: SupportedAuthorizationServiceDTO)

    @Query("SELECT * FROM supported_auth_services")
    fun getAllSupportedAuthorizationServices(): Flow<List<SupportedAuthorizationServiceDTO>>

}