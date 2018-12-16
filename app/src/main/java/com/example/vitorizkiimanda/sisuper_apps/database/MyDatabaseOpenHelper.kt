package com.example.vitorizkiimanda.sisuper_apps.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Agenda.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables

        db.createTable(AgendaEvent.TABLE_AGENDA_EVENT, true,
                AgendaEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                AgendaEvent.EVENT_ID to TEXT + UNIQUE,
                AgendaEvent.EVENT_IMAGE to TEXT,
                AgendaEvent.EVENT_DATE to TEXT,
                AgendaEvent.EVENT_NAME to TEXT,
                AgendaEvent.EVENT_PLACE to TEXT,
                AgendaEvent.EVENT_ORGANIZER to TEXT,
                AgendaEvent.EVENT_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(AgendaEvent.TABLE_AGENDA_EVENT, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)