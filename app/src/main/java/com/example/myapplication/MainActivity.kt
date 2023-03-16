package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.inbody.inbodysdk.IB_BleManager
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val _InBodyBLEManager = IB_BleManager.getInstance()

    private val height = 175.0
    private val weight = 66.5
    private val age = 40
    private val gender = "M"
    private val DEVICE_NAME = "InBodyBand2"

    private val REQUEST_BLUETOOTH_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        ) {
            _InBodyBLEManager.InitSDKWithCallback(
                height,
                weight,
                age,
                gender,
                DEVICE_NAME,
                true,
                this@MainActivity.baseContext
            )
            _InBodyBLEManager.SetCallback(CallbackInBodyBLEManager);
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSIONS)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    val CallbackInBodyBLEManager: IB_BleManager.BLECallback = object : IB_BleManager.BLECallback {
        override fun CallbackInitSDK(var1: JSONObject?) {}
        override fun CallbackSelectDevice(var1: JSONObject?) {}
        override fun CallbackRemoveDevice(var1: JSONObject?) {}
        override fun CallbackConnectDisconnect(var1: JSONObject?) {}
        override fun CallbackSetSync(var1: JSONObject?) {}
        override fun CallbackSetMobileNumber(var1: JSONObject?) {}
        override fun CallbackGetBcaData(var1: JSONObject?) {}
        override fun CallbackGetHRData(var1: JSONObject?) {}
        override fun CallbackGetActivityData(var1: JSONObject?) {}
        override fun CallbackGetSleepData(var1: JSONObject?) {}
        override fun CallbackStartBandInBodyTest(var1: JSONObject?) {}
        override fun CallbackStartBandHRTest(var1: JSONObject?) {}
        override fun CallbackSetProfileSync(var1: JSONObject?) {}
        override fun CallbackSetWait(var1: JSONObject?) {}
        override fun CallbackSetBand1Setting(var1: JSONObject?) {}
        override fun CallbackSetBand2Setting(var1: JSONObject?) {}
        override fun CallbackSetBandTimeAlarm(var1: JSONObject?) {}
        override fun CallbackSetEZtraining(var1: JSONObject?) {}
        override fun CallbackSetInBodyON(var1: JSONObject?) {}
        override fun CallbackSetInBodyH20(var1: JSONObject?) {}
        override fun CallbackGetPPGHR(var1: JSONObject?) {}
        override fun CallbackGetEcgRawData(var1: JSONObject?) {}
        override fun CallbackGetEcgRawDataCnt(var1: JSONObject?) {}
        override fun CallbackStartEcgPpg(var1: JSONObject?) {}
    }
}