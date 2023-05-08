package gg.jrg.audiminder.core.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gg.jrg.audiminder.core.data.dto.UserDetailsDTO

@Dao
interface UserDetailsDao {

    @Query("SELECT value FROM user_details WHERE `key` = :key")
    suspend fun getUserDetail(key: UserDetailsKey): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(userDetailsDTO: UserDetailsDTO)

    @Query("DELETE FROM user_details WHERE `key` = :key")
    suspend fun deleteUserDetail(key: UserDetailsKey)

}