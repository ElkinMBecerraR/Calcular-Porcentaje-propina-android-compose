package com.example.tiptime

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CalculadoraPantalla()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CalculadoraPantallaPrevista() {
    CalculadoraPantalla()
}

@Composable
fun CalculadoraPantalla() {
    var valorInput by remember { mutableStateOf("") }
    var textoInput by remember { mutableStateOf("") }

    var roundUp by remember { mutableStateOf(false) }
    var onRoundUpChanged by remember { mutableStateOf(false) }

    var valor = valorInput.toDoubleOrNull() ?: 0.0
    var parmPorcentaje = textoInput.toDoubleOrNull() ?: 0.0
    var porcentaje = calcularPorcentaje(valor, parmPorcentaje, roundUp)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip), modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        Input(
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            valorInput = valorInput,
            onValueChange = { valorInput = it },
            textoInput = stringResource(R.string.bill_amount),
            leadingIcon = R.drawable.money,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        Input(
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            valorInput = textoInput,
            onValueChange = { textoInput = it },
            textoInput = stringResource(R.string.how_was_the_service),
            leadingIcon = R.drawable.percent,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        personalizarPorcentaje(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 0.dp)
        )

        Text(
            text = stringResource(R.string.tip_amount, porcentaje),
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    valorInput: String,
    onValueChange: (String) -> Unit,
    textoInput: String
) {
    TextField(
        leadingIcon = {Icon(painter = painterResource(id = leadingIcon),null)},
        value = valorInput,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = textoInput) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun personalizarPorcentaje(
    modifier: Modifier = Modifier,
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }

}
@VisibleForTesting
internal fun calcularPorcentaje(valor: Double, porcentaje: Double, RoundUp: Boolean): String {
    var comision = porcentaje / 100 * valor
    if (RoundUp) {
        comision = kotlin.math.ceil(comision)
    }
    return NumberFormat.getCurrencyInstance().format(comision)
}
