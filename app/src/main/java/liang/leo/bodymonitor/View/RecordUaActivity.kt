package liang.leo.bodymonitor.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View_model.RecordUAViewModel
import liang.leo.bodymonitor.View_model.ViewRecordViewModel

class RecordUaActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var ua_text: EditText
    private lateinit var bs_text: EditText

    lateinit var viewModel: RecordUAViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_ua)
        viewModel = ViewModelProvider(this).get(RecordUAViewModel::class.java)

        initView()
        initControl()
    }

    private fun initView(){
        toolbar = findViewById(R.id.record_toolbar)
        toolbar.setTitle(R.string.RecordUricAcid)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            this.finish()
        })

        ua_text = findViewById(R.id.UA_ET)
        bs_text = findViewById(R.id.BS_ET)
    }

    private fun initControl(){
        ua_text.doOnTextChanged { text, _, _, _ -> viewModel.ua.postValue(text.toString().toInt()) }

        bs_text.doOnTextChanged { text, _, _, _ -> viewModel.bs.postValue(text.toString().toFloat()) }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.save_record ->{
                    viewModel.recordUA()
                    this.finish()
                    true
                }
                else -> {true}
            }
        }
    }
}