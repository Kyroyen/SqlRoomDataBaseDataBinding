package com.example.xlmp12

import android.app.Application
import com.example.xlmp12.database.AppDatabase

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}