package liang.leo.bodymonitor.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View_model.ViewRecordViewModel

class ViewRecordActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var graph_rb:RadioButton
    private lateinit var text_rb:RadioButton

    private lateinit var bp_rb:RadioButton
    private lateinit var ua_rb:RadioButton

    private lateinit var presentation_rg:RadioGroup
    private lateinit var type_rg:RadioGroup

    private lateinit var viewBPGraphFragment: ViewBPGraphFragment
    private lateinit var viewBPTextFragment: ViewBPTextFragment
    private lateinit var viewUAGraphFragment: ViewUAGraphFragment
    private lateinit var viewUATextFragment: ViewUATextFragment

    lateinit var viewModel:ViewRecordViewModel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_record)

        initFragment()
        initViews()

        viewModel = ViewModelProvider(this).get(ViewRecordViewModel::class.java)


    }

    private fun initFragment(){
        viewBPGraphFragment = ViewBPGraphFragment()
        viewUAGraphFragment = ViewUAGraphFragment()
        viewBPTextFragment = ViewBPTextFragment()
        viewUATextFragment = ViewUATextFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.view_record_content,viewBPTextFragment)
            .commit();


    }

    private fun initViews(){
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.ViewRecord)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            this.finish()
        })

        graph_rb = findViewById(R.id.view_graph_rb)

        text_rb = findViewById(R.id.view_text_rb)
        text_rb.isChecked = true

        bp_rb = findViewById(R.id.view_bp_rb)
        bp_rb.isChecked = true

        ua_rb = findViewById(R.id.view_ua_rb)

        presentation_rg = findViewById(R.id.data_presentation_radio_group)
        type_rg = findViewById(R.id.data_type_radio_group)

        presentation_rg.setOnCheckedChangeListener { radioGroup, i ->
            val transaction = this.supportFragmentManager.beginTransaction()
            when(i){
                R.id.view_text_rb ->{
                    if(bp_rb.isChecked){
                        transaction.replace(R.id.view_record_content, viewBPTextFragment)
                    }else{
                        transaction.replace(R.id.view_record_content, viewUATextFragment)
                    }
                }

                R.id.view_graph_rb ->{
                    if(bp_rb.isChecked){
                        transaction.replace(R.id.view_record_content, viewBPGraphFragment)
                    }else{
                        transaction.replace(R.id.view_record_content, viewUAGraphFragment)
                    }
                }
            }

            transaction.commit()
        }

        type_rg.setOnCheckedChangeListener { radioGroup, i ->
            val transaction = this.supportFragmentManager.beginTransaction()
            when(i){
                R.id.view_ua_rb ->{
                    if(graph_rb.isChecked){
                        transaction.replace(R.id.view_record_content, viewUAGraphFragment)
                    }else{
                        transaction.replace(R.id.view_record_content, viewUATextFragment)
                    }
                }

                R.id.view_bp_rb ->{
                    if(graph_rb.isChecked){
                        transaction.replace(R.id.view_record_content, viewBPGraphFragment)
                    }else{
                        transaction.replace(R.id.view_record_content, viewBPTextFragment)
                    }
                }
            }

            transaction.commit()
        }
    }

    private fun hideFragments(transaction: FragmentTransaction){
        transaction.hide(viewUAGraphFragment)
        transaction.hide(viewBPGraphFragment)
        transaction.hide(viewUATextFragment)
        transaction.hide(viewBPTextFragment)
    }
}