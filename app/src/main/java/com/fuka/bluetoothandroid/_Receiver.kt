package com.fuka.bluetoothandroid

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

class _Receiver : BroadcastReceiver() {
    val TAG = "Bluetooth fuka"

    override fun onReceive(context: Context, intent: Intent) {

        //Log.d(TAG, "permission: ${Manifest.permission.BLUETOOTH} ")
        //Log.d(TAG, "onReceive called ")
        val action: String? = intent.action
        //Log.d(TAG, "onReceive action: $action")
        when (action) {
            BluetoothDevice.ACTION_FOUND -> {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                Log.d(TAG, "found device: $device")

                if (device?.name != null) {
                    Log.d(TAG, "found device name: ${device?.name}")
                }

                Log.d(TAG, "Range signal strength RSS: $rssi")

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    //Log.d(TAG, "found device name: ${device?.name}")
                }
            }
        }
    }
}