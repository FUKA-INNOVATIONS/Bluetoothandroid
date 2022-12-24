package com.fuka.bluetoothandroid

import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel() : ViewModel() {
    val TAG = "Bluetooth fuka VM"

    companion object GattAttributes {
        const val SCAN_PERIOD: Long = 5000
        const val STATE_DISCONNECTED = 0
        const val STATE_CONNECTING = 1
        const val STATE_CONNECTED = 2
        val UUID_HEART_RATE_MEASUREMENT = UUID.fromString("000asd02902-3450000-103100-800990-00805456f9b34fb")
        val UUID_HEART_RATE_SERVICE = UUID.fromString("000asdsdf02902-345033000-1031400-8009904-008054564f9b34fb")
        val UUID_CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    }

/*    private val mResults = java.util.HashMap<String, ScanResult>() fun scanDevices(scanner: BluetoothLeScanner) {
        viewModelScope.launch(Dispatchers.IO) { fScanning.postValue(true)
            val settings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) .setReportDelay(0)
                .build()
            scanner.startScan(null, settings, leScanCallback) delay(SCAN_PERIOD) scanner.stopScan(leScanCallback) scanResults.postValue(mResults.values.toList()) fScanning.postValue(false)
        } }

    private val leScanCallback: ScanCallback = object : ScanCallback() { override fun onScanResult(callbackType: Int, result: ScanResult) {
        super.onScanResult(callbackType, result) val device = result.device
        val deviceAddress = device.address mResults!![deviceAddress] = result
        Log.d("DBG", "Device address: $deviceAddress (${result.isConnectable})") }
    }*/


}