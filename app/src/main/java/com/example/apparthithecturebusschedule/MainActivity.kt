package com.example.apparthithecturebusschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apparthithecturebusschedule.ui.BusScheduleApp
import com.example.apparthithecturebusschedule.ui.theme.AppArthithectureBUSSCHEDULETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppArthithectureBUSSCHEDULETheme {
                BusScheduleApp()
            }
        }
    }
}
