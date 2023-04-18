package gg.jrg.audiminder.authentication.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import gg.jrg.audiminder.authentication.data.dto.SupportedAuthorizationServiceDTO

@Dao
interface SupportedAuthorizationServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg supportedAuthorizationServiceDTO: SupportedAuthorizationServiceDTO)

}