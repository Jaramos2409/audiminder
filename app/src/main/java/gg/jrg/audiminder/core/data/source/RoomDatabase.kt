package gg.jrg.audiminder.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionCrossRefDTO
import gg.jrg.audiminder.collections.data.dto.AlbumCollectionDTO
import gg.jrg.audiminder.collections.data.dto.AlbumDTO
import gg.jrg.audiminder.collections.data.dto.TrackDTO
import gg.jrg.audiminder.collections.data.source.AlbumCollectionCrossRefDao
import gg.jrg.audiminder.collections.data.source.AlbumCollectionDao
import gg.jrg.audiminder.collections.data.source.AlbumDao
import gg.jrg.audiminder.collections.data.source.TrackDao
import gg.jrg.audiminder.core.data.Converters
import gg.jrg.audiminder.core.data.dto.UserDetailsDTO
import gg.jrg.audiminder.reminder.data.dto.ReminderDTO
import gg.jrg.audiminder.reminder.data.source.ReminderDao
import gg.jrg.audiminder.search.data.dto.SearchHistoryDTO
import gg.jrg.audiminder.search.data.source.SearchHistoryDao

@Database(
    entities = [
        AlbumCollectionDTO::class,
        AlbumDTO::class,
        TrackDTO::class,
        ReminderDTO::class,
        AlbumCollectionCrossRefDTO::class,
        SearchHistoryDTO::class,
        UserDetailsDTO::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumCollectionDao(): AlbumCollectionDao
    abstract fun albumDao(): AlbumDao
    abstract fun trackDao(): TrackDao
    abstract fun reminderDao(): ReminderDao
    abstract fun albumCollectionCrossRefDao(): AlbumCollectionCrossRefDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun userDetailsDao(): UserDetailsDao
}
