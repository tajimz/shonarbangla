package com.tajim.shonarbangla.others

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, CONSTANTS.sqliteName, null, CONSTANTS.sqliteVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            String.format(
                "CREATE TABLE %s (id INTEGER PRIMARY KEY AUTOINCREMENT, k22_price TEXT, k21_price TEXT, k18_price TEXT, k22_price_silver TEXT, k21_price_silver TEXT, k18_price_silver TEXT, date TEXT, k22_price_saudi TEXT, k21_price_saudi TEXT, k18_price_saudi TEXT)",
                CONSTANTS.sqlitePriceTable
            )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CONSTANTS.sqlitePriceTable))
        onCreate(db)
    }

    fun getData(): Cursor {
        val sqLiteDatabase = this.readableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM ${CONSTANTS.sqlitePriceTable}", null)
    }

    fun insertData(
        k22_price: String,
        k21_price: String,
        k18_price: String,
        k22_priceS: String,
        k21_priceS: String,
        k18_priceS: String,
        date: String,
        k22_priceSaudi: String,
        k21_priceSaudi: String,
        k18_priceSaudi: String
    ) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("k22_price", k22_price)
        contentValues.put("k21_price", k21_price)
        contentValues.put("k18_price", k18_price)
        contentValues.put("k22_price_silver", k22_priceS)
        contentValues.put("k21_price_silver", k21_priceS)
        contentValues.put("k18_price_silver", k18_priceS)
        contentValues.put("k22_price_saudi", k22_priceSaudi)
        contentValues.put("k21_price_saudi", k21_priceSaudi)
        contentValues.put("k18_price_saudi", k18_priceSaudi)
        contentValues.put("date", date)

        sqLiteDatabase.insert(CONSTANTS.sqlitePriceTable, null, contentValues)
    }

    fun clear() {
        val sqLiteDatabase = this.writableDatabase
        sqLiteDatabase.execSQL("DELETE FROM ${CONSTANTS.sqlitePriceTable}")
        sqLiteDatabase.close()
    }
}
