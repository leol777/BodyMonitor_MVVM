package liang.leo.bodymonitor.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View_model.ViewRecordViewModel
import liang.leo.bodymonitor.util.BodyMonitoringApplication
import liang.leo.bodymonitor.util.ItemRecyclerViewAdapter
import liang.leo.bodymonitor.util.LinearSpacingDecoration

class ViewUATextFragment : Fragment() {
    private val model: ViewRecordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_u_a_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.ua_text_recycler_view)
        val layoutManager = LinearLayoutManager(BodyMonitoringApplication.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(LinearSpacingDecoration(3))

        model.uaListliveData.observe(viewLifecycleOwner){
            val adapter = ItemRecyclerViewAdapter(BodyMonitoringApplication.context, it, item_type = 1)
            recyclerView.adapter = adapter
        }
    }
}