package com.example.tiptime1

import com.example.tiptime.calcularPorcentaje
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {

    @Test
    fun calculoPropina_20Porciento(){
        var valor = 10.00
        var porcentaje = 20.00
        var valorEsperado = NumberFormat.getCurrencyInstance().format(2)
        var actualValor = calcularPorcentaje(valor = valor, porcentaje = porcentaje, false)
        assertEquals(valorEsperado, actualValor)
    }
}