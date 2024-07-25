package com.example.tiptime;
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule;
import org.junit.Test
import java.text.NumberFormat

class TipUITests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calcular_20_porciento() {
        composeTestRule.setContent {
            TipTimeTheme {
                CalculadoraPantalla()
            }
        }
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        var valorEsperado = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText("Tip Amount: $valorEsperado")
            .assertExists("No node with this text was found")

    }
}


