package liang.leo.bodymonitor.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View_model.ViewRecordViewModel
import liang.leo.bodymonitor.util.LineChartManager

class ViewBPGraphFragment : Fragment() {
    private val model: ViewRecordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_b_p_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineCharManager:LineChartManager = LineChartManager(view.findViewById(R.id.bp_graph_chart))
        model.bpLowLiveData.observe(viewLifecycleOwner){
            lineCharManager.lineChart.clear()

        }
    }


}