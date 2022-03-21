package a.b.orbotintenttest

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import info.guardianproject.netcipher.proxy.OrbotHelper


class MainActivity : AppCompatActivity() {

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent?) {
            val status = intent?.getStringExtra(OrbotHelper.EXTRA_STATUS) ?: "unknown"
            tvLog.text =  "${tvLog.text}$status\n"
        }

    }

        private lateinit var tvLog: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            tvLog = findViewById(R.id.log)

            registerReceiver(receiver, IntentFilter(OrbotHelper.ACTION_STATUS))

        findViewById<Button>(R.id.btn).setOnClickListener {
            if (OrbotHelper.isOrbotInstalled(this)) {
//                Toast.makeText(this, "orbot installed", Toast.LENGTH_LONG).show()
                val helper = OrbotHelper.get(this).init()
                val intent = Intent(OrbotHelper.ACTION_STATUS).apply {
                    `package` = OrbotHelper.ORBOT_PACKAGE_NAME
                    putExtra(OrbotHelper.EXTRA_PACKAGE_NAME, packageName)
                }

                OrbotHelper.requestStartTor(this)


            } else {
                Toast.makeText(this, "orbot not installed", Toast.LENGTH_LONG).show()
            }
        }
    }


}