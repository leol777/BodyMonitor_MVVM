package liang.leo.bodymonitor.model

import android.content.Context
import android.util.Log
import liang.leo.bodymonitor.Item.Weather
import liang.leo.bodymonitor.util.DBHelper
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InterruptedIOException
import java.text.SimpleDateFormat
import java.util.*


class WeatherModel {

    private val api_key: String = "a91212c50f7041b9a9c68ea15874dbcc"
    private val weather_address: String =
            "https://devapi.qweather.com/v7/weather/now?key=$api_key"
    private val client: OkHttpClient = OkHttpClient()
    private val TAG: String = "WeatherModel"

    private var locationModel:LocationModel
    private val dbHelper:DBHelper

    private constructor(context: Context){
        locationModel = LocationModel.getInstance(context)
        dbHelper = DBHelper.getInstance(context)
    }

    companion object{
        var instance:WeatherModel? = null

        fun getInstance(context: Context):WeatherModel{
            if(instance == null){
                instance = WeatherModel(context)
            }

            return instance!!
        }
    }

    fun getWeather(latitude: Double, longitude: Double): Weather?{
        val formBody = FormBody.Builder().add("location", "$longitude,$latitude").build()
        val request = Request.Builder().url(weather_address).put(formBody).get().build()

        var receivedWeather:Boolean = false
        var weather: Weather? = null

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                receivedWeather = true
                Log.e(TAG, "onFailure: Failed to get weather information")
            }

            override fun onResponse(call: Call, response: Response) {
                receivedWeather = true
                weather = response.body?.let { onSuccessorsWeatherJson(it) }
                Log.e(TAG, "onResponse: ${weather?.toString()}")
            }
        })

        while (true){
            if(receivedWeather){
                return weather
            }else{
                try {
                    Thread.sleep(1)
                } catch (e:InterruptedIOException){
                    e.printStackTrace()
                }
            }
        }
    }

    fun getWeather(id:Int):Weather{
        return dbHelper.queryWeather(id)
    }

    private fun onSuccessorsWeatherJson(body: ResponseBody): Weather?{
        val obj:JSONObject = JSONArray(JSONObject(body.toString()).getJSONObject("HeWeather6")).getJSONObject(0)
        val basicObject = obj.getJSONObject("basic")
        val city:String = basicObject.getString("admin_area")

        val weatherObject = obj.getJSONObject("now")
        val desc:String = weatherObject.getString("cond_txt")
        val temp:Int = weatherObject.getInt("tmp")
        val feltTemp = weatherObject.getInt("fl")
        val humidity = weatherObject.getInt("hum")
        val windLevel = weatherObject.getInt("wind_sc")

        val dateObject = obj.getJSONObject("update")
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

        val date: Date = sdf.parse(dateObject.getString("loc"))

        return Weather(desc, temp, feltTemp, humidity, windLevel, date, city)

    }


}