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
import liang.leo.bodymonitor.View_model.RecordBPViewModel
import liang.leo.bodymonitor.View_model.RecordUAViewModel

class RecordBpActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var up_text:EditText
    private lateinit var low_text:EditText

    lateinit var viewModel: RecordBPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_bp)

        viewModel = ViewModelProvider(this).get(RecordBPViewModel::class.java)

        initView()
        initControl()

    }

    private fun initView(){
        toolbar = findViewById(R.id.record_toolbar)
        toolbar.setTitle(R.string.RecordBloopPressure)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            this.finish()
        })

        up_text = findViewById(R.id.UpBP_ET)
        low_text = findViewById(R.id.LowBP_ET)
    }

    private fun initControl(){
        up_text.doOnTextChanged { text, _, _, _ -> viewModel.upBP.postValue(text.toString().toInt()) }

        low_text.doOnTextChanged { text, _, _, _ -> viewModel.lowBP.postValue(text.toString().toInt()) }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.save_record ->{
                    viewModel.recordBP()
                    this.finish()
                    true
                }
                else -> {true}
            }
        }
    }
}