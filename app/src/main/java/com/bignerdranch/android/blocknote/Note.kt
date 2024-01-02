package com.bignerdranch.android.blocknote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note (@PrimaryKey val id: UUID = UUID.randomUUID(), var title: String = "", var value: String = "", var date: Date = Date()){
}