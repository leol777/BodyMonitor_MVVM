package liang.leo.bodymonitor.Item

import java.util.*

class UricAcidRecord(var uricAcid: Int, var bloodSugar: Float, var date: Date, override var type: Int = 1) : Record{
    var weather: Weather? = null
    constructor(uricAcid: Int, bloodSugar: Float, date: Date, weather: Weather)
            : this(uricAcid, bloodSugar,date) {
        this.weather = weather
    }
    override fun toString(): String {
        return "Uric Acid:$uricAcid, Blood Sugar:$bloodSugar, recorded on:${date.toString()}" +
                "Weather: ${weather.toString()}"
    }
}
