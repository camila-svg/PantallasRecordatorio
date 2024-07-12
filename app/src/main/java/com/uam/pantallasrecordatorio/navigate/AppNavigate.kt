package com.uam.pantallasrecordatorio.navigate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uam.pantallasrecordatorio.screen.AgregarRecordatorio
import com.uam.pantallasrecordatorio.screen.PantallaRecordatorio
import com.uam.pantallasrecordatorio.screen.Recordatorios

@Composable
fun AppNavigate() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { PantallaRecordatorio(navController = navController, padding = PaddingValues()) }
        composable("Recordatorio") { Recordatorios(navController) }
        composable("AgregarRecordatorio") { AgregarRecordatorio(navController) }


    }
}



