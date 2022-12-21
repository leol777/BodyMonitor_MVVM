package liang.leo.bodymonitor.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.Item.UricAcidRecord
import liang.leo.bodymonitor.Item.Weather
import java.text.SimpleDateFormat
import java.util.*

class DBHelper:SQLiteOpenHelper {
    private val TAG = "DBHelper"


    companion object{
        private val name:String = "BodyMonitorDB"
        private val version:Int = 1

        private const val mCreateBpDB:String = "create table bloodPressure" +
                "(date long not null primary key,upper_bp int not null,lower_bp int not null, weather int)"

        private const val mCreateUaDB:String = "create table uricAcid" +
                "(date long not null primary key,uric_acid int not null,blood_sugar float not null, weather int)"

        private const val mCreateWeatherDB:String = "create table weather" +
                "(id Integer primary key AUTOINCREMENT," +
                "date long not null, city TEXT not null, weather_desc Text not null, weather_temp int not null," +
                "feltTemp int not null, humidity int not null, windLevel int not null)"

        private const val BP_TABLE_NAME = "bloodPressure"
        private const val UA_TABLE_NAME = "uricAcid"
        private const val WEATHER_TABLE_NAME = "weather"
        private const val selectQuery = "SELECT * FROM "

        var instance:DBHelper? = null

        fun getInstance(context: Context):DBHelper{
            if(instance == null){
                instance = DBHelper(context)
            }

            return instance!!
        }
    }
    private constructor(context: Context)
            : super(context, name, null, version)

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(mCreateBpDB)
        p0?.execSQL(mCreateUaDB)
        p0?.execSQL(mCreateWeatherDB)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertBP(bp: BloodPressureRecord) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("date", bp.date.time)
        cv.put("upper_bp", bp.upperPressure)
        cv.put("lower_bp", bp.lowerPressure)
        cv.put("weather", bp.weather?.id)

        db.insert("bloodPressure", null, cv)
        Log.e(TAG, "insertBP: $bp has inserted")
    }

    fun insertUA(ua: UricAcidRecord){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("date", ua.date.time)
        cv.put("uric_acid", ua.uricAcid)
        cv.put("blood_sugar", ua.bloodSugar)
        cv.put("weather", ua.weather?.id)

        db.insert("uricAcid", null, cv)
        Log.e(TAG, "insertUA: $ua has inserted")

    }

    fun insertWeather(weather: Weather){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("id", weather.id)
        cv.put("city", weather.location)
        cv.put("date", weather.date?.time)
        cv.put("weather_desc", weather.desc)
        cv.put("weather_temp", weather.temp)
        cv.put("feltTemp", weather.feltTemp)
        cv.put("humidity", weather.humidity)
        cv.put("windLevel", weather.windLevel)

        db.insert("weather", null, cv)
    }

    fun queryAllBP():List<BloodPressureRecord>{
        val res = ArrayList<BloodPressureRecord>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery+ BP_TABLE_NAME, null)
        cursor.moveToNext()

        while(!cursor.isAfterLast){

            val bp = BloodPressureRecord(cursor.getInt(1), cursor.getInt(2), Date(cursor.getLong(0)))
            res.add(bp)

            cursor.moveToNext()
        }

        return res
    }

    fun queryBP(date: Date): BloodPressureRecord {
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery+ BP_TABLE_NAME+" WHERE date = " + date.time, null)

        return BloodPressureRecord(cursor.getInt(1), cursor.getInt(2), date)
    }

    fun queryAllUA():List<UricAcidRecord>{
        val res = ArrayList<UricAcidRecord>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery+ UA_TABLE_NAME, null)
        cursor.moveToNext()

        while(!cursor.isAfterLast){
            val ua = UricAcidRecord(cursor.getInt(1), cursor.getFloat(2), Date(cursor.getLong(0)))
            res.add(ua)

            cursor.moveToNext()
        }

        return res
    }

    fun queryUA(date: Date): UricAcidRecord {
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery+ UA_TABLE_NAME+" WHERE date = " + date.time, null)

        return UricAcidRecord(cursor.getInt(1), cursor.getFloat(2), date)
    }

    fun queryWeather(id: Int): Weather {
        val db = this.writableDatabase
        val cursor = db.rawQuery("$selectQuery$WEATHER_TABLE_NAME WHERE id = $id", null)

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = simpleDateFormat.parse(cursor.getString(1))

        return Weather(
            id, cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
            cursor.getInt(6), cursor.getInt(7), date, cursor.getString(2)
        )
    }
}