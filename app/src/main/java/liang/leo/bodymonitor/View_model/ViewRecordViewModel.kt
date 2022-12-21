package liang.leo.bodymonitor.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.Item.UricAcidRecord
import liang.leo.bodymonitor.model.BPModel
import liang.leo.bodymonitor.model.UAModel
import liang.leo.bodymonitor.util.BodyMonitoringApplication

class ViewRecordViewModel: ViewModel() {
    val uaListliveData:MutableLiveData<List<UricAcidRecord>> =
        UAModel.getInstance(BodyMonitoringApplication.context).getAllUA()

    val bpListliveData:MutableLiveData<List<BloodPressureRecord>> =
        BPModel.getInstance(BodyMonitoringApplication.context).getAllBP()

    private val bp_pair = toBPArray(bpListliveData)
    private val ua_pair = toUAArray(uaListliveData)

    val bpUpLiveData = bp_pair.first
    val bpLowLiveData = bp_pair.second

    val uaUricAcidLiveData = ua_pair.first
    val uaBloodSugarLiveData = ua_pair.second

    private fun toBPArray(bpListliveData:MutableLiveData<List<BloodPressureRecord>>)
                    :Pair<MutableLiveData<IntArray>, MutableLiveData<IntArray>>{
        var upIntArray = bpListliveData.value?.let { IntArray(it.size) } ?: IntArray(0)
        val lowIntArray = bpListliveData.value?.let { IntArray(it.size) } ?: IntArray(0)

        if(bpListliveData.value != null){
            for((i,bp:BloodPressureRecord) in bpListliveData.value!!.withIndex()){
                upIntArray?.set(i, bp.upperPressure)
                lowIntArray?.set(i, bp.lowerPressure)
            }
        }

        val up = MutableLiveData<IntArray>(upIntArray)
        val low = MutableLiveData<IntArray>(lowIntArray)
        val pair:Pair<MutableLiveData<IntArray>, MutableLiveData<IntArray>> = Pair(up,low)
        return pair
    }

    private fun toUAArray(uaListliveData:MutableLiveData<List<UricAcidRecord>>)
            :Pair<MutableLiveData<IntArray>, MutableLiveData<FloatArray>>{
        val uaIntArray = uaListliveData.value?.let { IntArray(it.size) } ?: IntArray(0)
        val bsFloatArray = uaListliveData.value?.let { FloatArray(it.size) } ?: FloatArray(0)

        if(uaListliveData.value != null){
            for((i,ua:UricAcidRecord) in  uaListliveData.value!!.withIndex()){
                uaIntArray?.set(i, ua.uricAcid)
                bsFloatArray?.set(i, ua.bloodSugar)
            }
        }

        val ua = MutableLiveData<IntArray>(uaIntArray)
        val bs = MutableLiveData<FloatArray>(bsFloatArray)
        val pair:Pair<MutableLiveData<IntArray>, MutableLiveData<FloatArray>> = Pair(ua,bs)
        return pair
    }
}