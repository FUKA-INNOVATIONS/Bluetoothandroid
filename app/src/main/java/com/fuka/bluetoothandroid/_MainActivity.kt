package com.fuka.bluetoothandroid

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.fuka.bluetoothandroid.ui.theme.BluetoothAndroidTheme

class _MainActivity : ComponentActivity() {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var br: BroadcastReceiver
    private lateinit var deviceViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "Bluetooth fuka"


        br = _Receiver()

        val filterFound = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(br, filterFound)

        /*val filterDiscovering = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        registerReceiver(br, filterDiscovering)*/



        bluetoothManager = getSystemService(BluetoothManager::class.java)
        //bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter


        if (bluetoothAdapter == null) {
            Log.d(TAG, "Device doesn't support bluetooth")
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission not granted")
        }


        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            Log.d(TAG, "paired device: $device")
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            Log.d(TAG, "paired device name: $deviceName")
            Log.d(TAG, "paired device MAC: $deviceHardwareAddress")
        }




        super.onCreate(savedInstanceState)
        setContent {

            BluetoothAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {

                        Button(onClick = {
                            bluetoothAdapter.startDiscovery()
                            Log.d(TAG, "scan mode: ${bluetoothAdapter.scanMode}")
                            Log.d(TAG, "bonded devices: ${bluetoothAdapter.bondedDevices}")
                            Log.d(TAG, "isDiscovering: ${bluetoothAdapter.isDiscovering}")

                        }) {
                            Text(text = "Start scan")
                        }



                    }
                }

            }
        }
    }

    override fun onDestroy() { // TODO : test is it working?
        super.onDestroy()
        unregisterReceiver(br)
        Log.d("Bluetooth fuka", "onDestroy: MainActivity")
    }

}