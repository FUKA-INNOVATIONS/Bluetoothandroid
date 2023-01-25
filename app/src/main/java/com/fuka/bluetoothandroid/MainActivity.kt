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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.fuka.bluetoothandroid.ui.theme.BluetoothAndroidTheme



class MainActivity : ComponentActivity() {
    val TAG = "Bluetooth fuka"
    val model = BluetoothViewModel()

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filterFound = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(model.receiver, filterFound)

        bluetoothManager = getSystemService(BluetoothManager::class.java)
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


        setContent {
            val isScanEnabled = rememberSaveable {
                mutableStateOf((bluetoothAdapter.isDiscovering))
            }
            BluetoothAndroidTheme {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)) {

                    Button(
                        //enabled = isScanEnabled.value,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            bluetoothAdapter.cancelDiscovery()
                            model.clearDiscoveredDeviceList()
                            bluetoothAdapter.startDiscovery()
                            Log.d(TAG, "scan mode: ${bluetoothAdapter.scanMode}")
                            Log.d(TAG, "bonded devices: ${bluetoothAdapter.bondedDevices}")
                            Log.d(TAG, "isDiscovering: ${bluetoothAdapter.isDiscovering}")

                        }) {
                        Text(text = "Start scan")
                    }
                    /*Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { model.addPhone(Phone("Android", 4444)) }) {
                        Text(text = "Add new phone")
                    }*/

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { model.printDeviceList() }) {
                        Text(text = "Log device list size")
                    }

                    LazyColumn() {
                        items(items = model.deviceListDiscovered) { phone ->

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(modifier = Modifier.weight(1f), text = phone.address)
                                Text(text = phone.rss.toString())
                                IconButton(onClick = {Log.d(TAG, "Clicked")}) {
                                    Icon(Icons.Filled.Send, contentDescription = "Connect")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onPause() { // TODO : test is it working?
        Log.d(TAG, "onDestroy: MainActivity")
        super.onPause()
        unregisterReceiver(model.receiver)
    }
}
