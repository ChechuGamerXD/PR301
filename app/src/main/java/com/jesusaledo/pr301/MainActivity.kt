package com.jesusaledo.pr301

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusaledo.pr301.ui.theme.PR301Theme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR301Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                ) {
                    Alcohol()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Alcohol() {

    var perjudicado by rememberSaveable {
        mutableStateOf("")
    }
    var diametro by rememberSaveable {
        mutableStateOf("")
    }
    var altura by rememberSaveable {
        mutableStateOf("")
    }
    var vasos by rememberSaveable {
        mutableStateOf("")
    }
    var secure by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ingrese los datos requeridos para comprobar si estás \"afectado\"")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = perjudicado,
            onValueChange = {perjudicado = it; secure = false },
            label = { Text("Litros para estar perjudicado") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = diametro,
            onValueChange = { diametro = it; secure = false },
            label = { Text("Diámetro del vaso") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it; secure = false },
            label = { Text("Altura del baso") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = vasos,
            onValueChange = { vasos = it; secure = false },
            label = { Text("Cantidad de vasos bebidos") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                secure = perjudicado.isNotBlank() && perjudicado.toDoubleOrNull() != null && perjudicado.toDouble() > 0 &&
                        diametro.isNotBlank() && diametro.toDoubleOrNull() != null && diametro.toDouble() > 0 &&
                        altura.isNotBlank() && altura.toDoubleOrNull() != null && altura.toDouble() > 0 &&
                        vasos.isNotBlank() && vasos.toIntOrNull() != null && vasos.toInt() >= 0
                      },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Calcular")
        }
        if (secure) {
            Text("Información sobre tu ebriedad:")
            Spacer(modifier = Modifier.height(8.dp))
            val radio = diametro.toDouble() / 2
            val volumen = Math.PI * (radio * radio) * altura.toDouble()
            val vasosPerjudicado = ((perjudicado.toDouble() * 1000) / volumen).roundToInt()
            Text("Necesitas tomar $vasosPerjudicado vasos para estar perjudicado")
            if (vasos.toInt() == 0)
                Text("Mejor sigue así y no bebas nada")
            else if (vasos.toInt() < vasosPerjudicado)
                Text("Te faltan ${vasosPerjudicado - vasos.toInt()} vasos para acabar perjudicado")
            else
                Text("Ya estás perjudicado, no conduzcas")
        }
        else
            Text("Introduce valores mayores que 0, y los vasos no deben ser decimales, en este mundo bebemos" +
                    " los vasos enteros")
    }
}
@Preview(showBackground = true)
@Composable
fun AlcoholPreview() {
    PR301Theme {
        Alcohol()
    }
}