package liang.leo.bodymonitor.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import liang.leo.bodymonitor.Item.UricAcidRecord
import liang.leo.bodymonitor.util.DBHelper
import java.util.*

class UAModel {
    private var dbHelper:DBHelper
    companion object{
        var instance:UAModel? = null
        fun getInstance(context: Context):UAModel{
            if(instance == null){
                instance = UAModel(context)
            }

            return instance!!
        }
    }

    constructor(context: Context){
        dbHelper = DBHelper.getInstance(context)
    }

    fun getAllUA():MutableLiveData<List<UricAcidRecord>>{
        val liveData:MutableLiveData<List<UricAcidRecord>> = MutableLiveData()
        liveData.postValue(dbHelper.queryAllUA())
        return liveData
    }

    fun getSingleUA(date:Date): MutableLiveData<UricAcidRecord> {
        val liveData:MutableLiveData<UricAcidRecord> = MutableLiveData()
        liveData.postValue(dbHelper.queryUA(date))
        return liveData
    }

    fun insertUA(ua: UricAcidRecord){
        dbHelper.insertUA(ua)
    }
}