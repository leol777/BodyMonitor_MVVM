package liang.leo.bodymonitor.View_model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.Item.UricAcidRecord
import liang.leo.bodymonitor.model.UAModel
import liang.leo.bodymonitor.util.BodyMonitoringApplication
import java.util.*

class RecordUAViewModel:ViewModel() {
    private val TAG = "RecordBPViewModel"
    val ua:MutableLiveData<Int> = MutableLiveData()
    val bs:MutableLiveData<Float> = MutableLiveData()

    fun recordUA():Boolean{
        var valid_ua = false
        var valid_bs = false
        when{
            ua.value == null -> makeToast("尿酸不能为空")
            ua.value!! >= 500 -> makeToast("尿酸值过高，请检查输入")
            ua.value!! < 0 -> makeToast("尿酸值不能为负")
            else -> valid_ua = true
        }

        when{
            bs.value == null -> makeToast("血糖不能为空")
            bs.value!! >= 10 -> makeToast("血糖值过高，请检查输入")
            bs.value!! < 0 -> makeToast("血糖值不能为负")
            else -> valid_bs = true
        }

        return if(valid_ua && valid_bs) realRecordUA() else false

    }

    private fun realRecordUA():Boolean{
        Log.e(TAG, "recordUA: ${ua.value}, ${bs.value}", )
        val uaModel = UAModel.getInstance(BodyMonitoringApplication.context)
        val date = Date()

        val ua: UricAcidRecord? = ua.value?.let { bs.value?.let { it1 -> UricAcidRecord(it, it1, date) } }

        if(ua != null){
            uaModel.insertUA(ua)
            return true
        }

        return false
    }

    private fun makeToast(messeage:String){
        Toast.makeText(BodyMonitoringApplication.context, messeage, Toast.LENGTH_SHORT).show()
    }
}