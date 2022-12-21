package liang.leo.bodymonitor.View_model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.model.BPModel
import liang.leo.bodymonitor.util.BodyMonitoringApplication
import java.util.*

class RecordBPViewModel:ViewModel() {
    private val TAG = "RecordBPViewModel"
    val upBP:MutableLiveData<Int> = MutableLiveData()
    val lowBP:MutableLiveData<Int> = MutableLiveData()

    fun recordBP():Boolean{
        var valid_up = false
        var valid_low = false

        when{
            upBP.value == null -> makeToast("上压不能为空")
            upBP.value!! >= 250 -> makeToast("上压过高，请检查输入")
            upBP.value!! < 0 -> makeToast("上压不可为负数")
            else -> valid_up = true
        }

        when{
            lowBP.value == null -> makeToast("下压不能为空")
            upBP.value != null && lowBP.value!! > upBP.value!! -> makeToast("下压不可高于上压，请检查输入")
            lowBP.value!! < 0 -> makeToast("下压不可为负数")
            else -> valid_low = true
        }

        return if(valid_low && valid_up) realRecordBP() else false
    }

    private fun realRecordBP():Boolean{
        Log.e(TAG, "recordBP: ${upBP.value}, ${lowBP.value}", )
        val bpModel = BPModel.getInstance(BodyMonitoringApplication.context)
        val date = Date()

        val bp:BloodPressureRecord? = upBP.value?.let { lowBP.value?.let { it1 -> BloodPressureRecord(it, it1, date) } }

        if(bp != null){
            bpModel.insertBP(bp)
            return true
        }

        return false

    }

    private fun makeToast(messeage:String){
        Toast.makeText(BodyMonitoringApplication.context, messeage, Toast.LENGTH_SHORT).show()
    }
}