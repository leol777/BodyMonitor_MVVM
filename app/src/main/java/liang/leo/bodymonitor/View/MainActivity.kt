package liang.leo.bodymonitor.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import liang.leo.bodymonitor.R
import liang.leo.bodymonitor.View.RecordBpActivity
import liang.leo.bodymonitor.View.RecordUaActivity
import liang.leo.bodymonitor.View.ViewRecordActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mRecordBpButton:Button
    private lateinit var mRecordUaButton:Button
    private lateinit var mViewRecordButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecordBpButton = findViewById<Button>(R.id.Record_BP_Button)
        mRecordUaButton = findViewById(R.id.Record_UA_Button)
        mViewRecordButton = findViewById(R.id.View_Record_Button)

        mRecordBpButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RecordBpActivity::class.java)
            this.startActivity(intent)
        })

        mRecordUaButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RecordUaActivity::class.java)
            this.startActivity(intent)
        })

        mViewRecordButton.setOnClickListener(View.OnClickListener {
            val  intent = Intent(this, ViewRecordActivity::class.java)
            this.startActivity(intent)
        })

    }
}