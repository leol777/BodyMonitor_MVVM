package liang.leo.bodymonitor.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import liang.leo.bodymonitor.Item.BloodPressureRecord
import liang.leo.bodymonitor.Item.Record
import liang.leo.bodymonitor.Item.UricAcidRecord
import liang.leo.bodymonitor.R
import java.text.SimpleDateFormat


class ItemRecyclerViewAdapter(private val context: Context, private val list:List<Record>, val item_type:Int)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class UAViewHolder(itemView: View, context: Context) :RecyclerView.ViewHolder(itemView){
        val title1:TextView = itemView.findViewById(R.id.info_title1)
        val title2:TextView = itemView.findViewById(R.id.info_title2)
        val val1:TextView = itemView.findViewById(R.id.info_value1)
        val val2:TextView = itemView.findViewById(R.id.info_value2)
        val unit1:TextView = itemView.findViewById(R.id.info_unit1)
        val unit2:TextView = itemView.findViewById(R.id.info_unit2)
        val date:TextView = itemView.findViewById(R.id.item_date)

        init {
            title1.text = context.getString(R.string.UricAcid)
            title2.text = context.getString(R.string.BloodSugar)

            unit1.text = context.getString(R.string.UAUnit)
            unit2.text = context.getString(R.string.BSUnit)
        }


    }

    class BPViewHolder(itemView: View, context: Context):RecyclerView.ViewHolder(itemView){
        val title1:TextView = itemView.findViewById(R.id.info_title1)
        val title2:TextView = itemView.findViewById(R.id.info_title2)
        val val1:TextView = itemView.findViewById(R.id.info_value1)
        val val2:TextView = itemView.findViewById(R.id.info_value2)
        val unit1:TextView = itemView.findViewById(R.id.info_unit1)
        val unit2:TextView = itemView.findViewById(R.id.info_unit2)
        val date:TextView = itemView.findViewById(R.id.item_date)

        init {
            title1.text = context.getString(R.string.UpperBP)
            title2.text = context.getString(R.string.LowerBP)

            unit1.text = context.getString(R.string.BPUnit)
            unit2.text = context.getString(R.string.BPUnit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            liang.leo.bodymonitor.R.layout.text_item_view,
            parent,
            false
        )

        var vh:RecyclerView.ViewHolder
        if(item_type == 0){
            vh = BPViewHolder(view,context)
        }else{
            vh = UAViewHolder(view,context)
        }

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        if(item_type == 0){
            val item = list.get(position) as BloodPressureRecord
            holder as BPViewHolder
            holder.val1.text = item.upperPressure.toString()
            holder.val2.text = item.lowerPressure.toString()
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            holder.date.text = sdf.format(item.date)
        }else{
            val item = list.get(position) as UricAcidRecord
            holder as UAViewHolder
            holder.val1.text = item.uricAcid.toString()
            holder.val2.text = item.bloodSugar.toString()
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            holder.date.text = sdf.format(item.date)
        }

    override fun getItemCount(): Int {
        return list.size
    }
}