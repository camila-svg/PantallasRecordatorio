package com.uam.pantallasrecordatorio.screen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.uam.pantallasrecordatorio.navigate.Recordatorio
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uam.pantallasrecordatorio.R
import com.uam.pantallasrecordatorio.viewmodel.DetailRecordatorioViewModel
import com.uam.pantallasrecordatorio.viewmodel.DetailRecordatorioViewModelFactory

import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recordatorios(navController: NavHostController) {
    var nombreRecordatorio by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(
                onClick = { /* TODO: Handle back button click */ },
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    "< Regresar",
                    color = Color(0xFF5A5A5A),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Recordatorios",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6D6D6D)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.osito), // Reemplaza con tu recurso de imagen
                contentDescription = "Panda",
                modifier = Modifier.size(190.dp) // Ajusta el tamaño de la imagen según sea necesario
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5E8C8)),
                modifier = Modifier
                    .height(150.dp) // Ajusta la altura del botón según sea necesario
                    .weight(1f) // Ajusta el ancho del botón según sea necesario
            ) {
                Text(text = "Nuevos Recordatorio", color = Color(0xFF6D6D6D), fontSize = 25.sp)
            }

            Spacer(modifier = Modifier.weight(2f)) // Empuja el botón hacia la parte inferior
            Button(
                onClick = { navController.navigate("AgregarRecordatorio") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D6D6D)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "+", color = Color.White, fontSize = 32.sp)
            }
        }

    }
}

@Composable
fun DetailRecordatorioScreen(padding: PaddingValues, navController: NavHostController, idRecordatorio: String) {

    val context = LocalContext.current

    val detailRecordatorioModel = viewModel(
        DetailRecordatorioViewModel::class.java,
        factory = DetailRecordatorioViewModelFactory(context,idRecordatorio)
    )

    val dato = detailRecordatorioModel.recordatorioMutable

    val state = detailRecordatorioModel.mstate

    LaunchedEffect(key1 = state.mensaje) {
        if (state.mensaje != null){
            Toast.makeText(context,state.mensaje, Toast.LENGTH_LONG).show()
            if (!state.error) {
                navController.navigate(Recordatorio)
            }
            detailRecordatorioModel.resetDato()
        }
    }

    Log.d("COMPOSE","RECORDATORIO ${dato}")
    Box(modifier = Modifier.padding(padding)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustomField(
                name = dato.id.toString(),
                pLabel = stringResource(id = R.string.id),
                onNameChange = { detailRecordatorioModel.onId(it.toIntOrNull() ?: 0) }
            )
            TextCustomField(
                name = dato.nombreActividad,
                pLabel = stringResource(id = R.string.nombreActividad),
                onNameChange = { detailRecordatorioModel.onNombreActividad(it) }
            )
            TextCustomField(
                name = dato.fechaHora,
                pLabel = stringResource(id = R.string.FechaHora),
                onNameChange = { detailRecordatorioModel.onFechaHora(it) }
            )
            TextCustomField(
                name = dato.realizado.toString(),
                pLabel = stringResource(id = R.string.realizado),
                onNameChange = { detailRecordatorioModel.onRealizado(it.toBoolean()) }
            )
        }
    }

    Row()
    {
        Button(onClick = {detailRecordatorioModel.onSave(idRecordatorio)}) {
            Text(text= stringResource(id = R.string.saveAgenda))
        }
        Button(onClick = {navController.popBackStack()}) {
            Text(text= stringResource(id = R.string.cancelBoton))
        }
    }

}

@Composable
fun TextCustomField(
    name: String,
    pLabel: String,
    onNameChange: (String) -> Unit
) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text(pLabel) }
    )
}