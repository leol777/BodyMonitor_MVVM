package liang.leo.bodymonitor.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View_model.ViewRecordViewModel
import liang.leo.bodymonitor.util.BodyMonitoringApplication
import liang.leo.bodymonitor.util.ItemRecyclerViewAdapter
import liang.leo.bodymonitor.util.LinearSpacingDecoration

class ViewBPTextFragment : Fragment() {
    private val model: ViewRecordViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_b_p_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.bp_text_recycler_view)
        val layoutManager = LinearLayoutManager(BodyMonitoringApplication.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(LinearSpacingDecoration(3))

        model.bpListliveData.observe(viewLifecycleOwner){
            val adapter = ItemRecyclerViewAdapter(BodyMonitoringApplication.context, it, item_type = 0)
            recyclerView.adapter = adapter
        }
    }


}