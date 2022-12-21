package liang.leo.bodymonitor.Item

import java.util.*

class Weather(var id:Int) {

    var desc:String = ""
    var temp:Int = Int.MAX_VALUE
    var feltTemp:Int = Int.MAX_VALUE
    var humidity:Int = Int.MAX_VALUE
    var windLevel:Int = Int.MAX_VALUE
    var date: Date? = null
    var location:String = ""

    constructor(id:Int, desc:String, temp:Int, feltTemp:Int, humidity:Int, windLevel:Int,
                date: Date, location:String):this(id){
                this.desc = desc
                this.feltTemp = feltTemp
                this.humidity = humidity
                this.windLevel = windLevel
                this.date =date
                this.location = location}


    constructor(
        desc:String, temp:Int, feltTemp:Int, humidity:Int, windLevel:Int,
        date: Date, location:String) :
            this(-1, desc, temp, feltTemp, humidity, windLevel, date, location) {}


    override fun toString(): String {
        if(date == null){
            return "Weather id $id not initialized."
        }
        return "Weather recorded on ${date.toString()}, at location: $location. " +
                "Weather is $desc, Temperature:$temp, Feeling temperature:$feltTemp, humidity:$humidity%, wind level:$windLevel"
    }
}
