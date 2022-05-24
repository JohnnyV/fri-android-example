package com.example.fri

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.fri.fcm.MyFcmService
import com.example.fri.lib.api.ImportantApi
import java.util.*

class MainActivity : FragmentActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.btn_important).setOnClickListener {
            val importantIterator = ServiceLoader.load(ImportantApi::class.java, ImportantApi::class.java.classLoader).iterator()
            val important = importantIterator.next()

            Toast.makeText(this, important.importantInfo(), Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.btn_log_fcm_token).setOnClickListener {
            MyFcmService.logToken()
        }
    }
}
