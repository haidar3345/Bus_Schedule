package com.example.apparthithecturebusschedule.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apparthithecturebusschedule.R
import com.example.apparthithecturebusschedule.data.BusSchedule
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class BusScheduleScreen {
    FullSchedule,
    RouteDetail
}

@Composable
fun BusScheduleApp(
    viewModel: BusScheduleViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = BusScheduleViewModel.Factory)
) {
    val navController = rememberNavController()
    val fullSchedule by viewModel.getFullSchedule().collectAsState(emptyList())

    NavHost(
        navController = navController,
        startDestination = BusScheduleScreen.FullSchedule.name
    ) {
        composable(BusScheduleScreen.FullSchedule.name) {
            FullScheduleScreen(
                busSchedules = fullSchedule,
                onScheduleClick = { stopName ->
                    navController.navigate("${BusScheduleScreen.RouteDetail.name}/$stopName")
                }
            )
        }
        composable(
            route = "${BusScheduleScreen.RouteDetail.name}/{stopName}",
            arguments = listOf(navArgument("stopName") { type = NavType.StringType })
        ) { backStackEntry ->
            val stopName = backStackEntry.arguments?.getString("stopName") ?: ""
            val stopSchedule by viewModel.getScheduleFor(stopName).collectAsState(emptyList())
            RouteDetailScreen(
                stopName = stopName,
                busSchedules = stopSchedule,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScheduleScreen(
    busSchedules: List<BusSchedule>,
    onScheduleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Jadwal Bus Indonesia") })
        }
    ) { innerPadding ->
        BusScheduleList(
            busSchedules = busSchedules,
            onScheduleClick = onScheduleClick,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreen(
    stopName: String,
    busSchedules: List<BusSchedule>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stopName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        BusScheduleList(
            busSchedules = busSchedules,
            onScheduleClick = {},
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BusScheduleList(
    busSchedules: List<BusSchedule>,
    onScheduleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
        items(busSchedules) { schedule ->
            BusScheduleItem(
                busSchedule = schedule,
                onScheduleClick = onScheduleClick
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun BusScheduleItem(
    busSchedule: BusSchedule,
    onScheduleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onScheduleClick(busSchedule.stopName) }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = busSchedule.stopName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = formatTime(busSchedule.arrivalTime),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
    }
}

fun formatTime(arrivalTime: Int): String {
    val hours = arrivalTime / 3600
    val minutes = (arrivalTime % 3600) / 60
    return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
}
