package gg.jrg.audiminder.reminder.domain.model

import java.util.*

data class Reminder(
    val reminderId: Int,
    val name: String,
    val time: Date,
    val notes: String?,
    val targetType: String,
    val targetId: Int
)
