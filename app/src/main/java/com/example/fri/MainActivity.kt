package com.example.fri

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.fri.fcm.MyFcmService
import com.example.fri.lib.api.ImportantApi
import java.util.*

class MainActivity : FragmentActivity(R.layout.activity_main) {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        Toast.makeText(this, "Notification permission ${if (isGranted) "granted" else "denied"}", Toast.LENGTH_SHORT).show()
    }

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

        findViewById<Button>(R.id.btn_request_permission).setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        // Since Android 13 (API level 33) notification permission is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Toast.makeText(this, "Notification permission already granted", Toast.LENGTH_SHORT).show()
        }
    }
}
