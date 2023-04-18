package gg.jrg.audiminder.reminder.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import gg.jrg.audiminder.core.data.DomainMappable
import gg.jrg.audiminder.reminder.domain.model.Reminder
import java.util.*

@Entity(tableName = "reminders")
data class ReminderDTO(
    @PrimaryKey(autoGenerate = true) val reminderId: Int,
    val name: String,
    val time: Date,
    val notes: String?,
    val targetType: String,
    val targetId: Int
) : DomainMappable<Reminder> {
    override fun asDomainModel(): Reminder = Reminder(
        reminderId = this.reminderId,
        name = this.name,
        time = this.time,
        notes = this.notes,
        targetType = this.targetType,
        targetId = this.targetId
    )
}