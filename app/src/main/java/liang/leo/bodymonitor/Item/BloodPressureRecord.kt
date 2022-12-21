package liang.leo.bodymonitor.Item

import java.util.*

class BloodPressureRecord(var upperPressure: Int, var lowerPressure: Int, var date:Date,
                          override var type: Int = 0
) : Record{
    var weather: Weather? = null
    constructor(upperPressure: Int, lowerPressure: Int, date: Date, weather: Weather)
            : this(upperPressure, lowerPressure,date) {
                this.weather = weather
    }

    init {
        type = 1
    }
    override fun toString(): String {
        return "Upper pressure:$upperPressure, Lower pressure:$lowerPressure, recorded on:${date.toString()}" +
                "Weather: ${weather.toString()}"
    }
}