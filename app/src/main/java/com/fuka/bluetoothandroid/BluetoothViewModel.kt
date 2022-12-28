package com.fuka.bluetoothandroid

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow

class BluetoothViewModel() : ViewModel() {
    val TAG = "Bluetooth fuka"

    private val _discoveredDeviceList: MutableList<Phone> = mutableListOf<Phone>().toMutableStateList()

    val deviceListDiscovered: List<Phone>
        get() = _discoveredDeviceList

    fun addPhone(phone: Phone) {
        _discoveredDeviceList.add(phone)
    }

    init {
        //_testList.add(Phone("Nokia", 2323))
    }

    fun clearDiscoveredDeviceList() { _discoveredDeviceList.clear() }
    fun printDeviceList() { Log.d(TAG, "${_discoveredDeviceList.size}") }


    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                    Log.d(TAG, "found device0: $device")

                    if (device?.name != null) {
                        Log.d(TAG, "found device name: ${device?.name}")
                    }

                    Log.d(TAG, "Range signal strength RSS: $rssi")

                    if (device != null) {
                        addPhone(Phone(device.name ?: device.address, rssi.toInt()))
                    }

                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    }
                }
            }
        }
    }
}

data class Phone(val address: String, val rss: Int)