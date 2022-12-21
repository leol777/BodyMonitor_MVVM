package liang.leo.bodymonitor.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.util.DBHelper
import java.util.*

class BPModel {
    private var dbHelper: DBHelper
    companion object{
        var instance:BPModel? = null

        fun getInstance(context: Context):BPModel{
            if(instance == null){
                instance = BPModel(context)
            }

            return instance!!
        }
    }

    constructor(context: Context){
        dbHelper = DBHelper.getInstance(context)
    }

    fun getAllBP():MutableLiveData<List<BloodPressureRecord>>{
        val liveData:MutableLiveData<List<BloodPressureRecord>> = MutableLiveData()
        liveData.postValue(dbHelper.queryAllBP())
        return liveData
    }

    fun getSingleBP(date: Date): MutableLiveData<BloodPressureRecord> {
        val liveData:MutableLiveData<BloodPressureRecord> = MutableLiveData()
        liveData.postValue(dbHelper.queryBP(date))
        return liveData
    }

    fun insertBP(bp: BloodPressureRecord){
        dbHelper.insertBP(bp)
    }
}