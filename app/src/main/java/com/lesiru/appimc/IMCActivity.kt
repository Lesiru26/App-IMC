package com.lesiru.appimc

import android.content.Intent
import android.graphics.Color
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.lesiru.appimc.R
import com.lesiru.appimc.ResultIMCActivity

class IMCActivity : AppCompatActivity() {

    //atributos
    private lateinit var viewMale: CardView //no te inicies ahora -> lateinit
    private lateinit var viewFemale: CardView
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentHeight: Int = 120
    private var currentWeight: Int = 70
    private var currentAge: Int = 25
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    companion object {
        //objeto estatico
        const val IMC_KEY = "IMC_RESULT"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imcactivity)
        initComponents() //se inician y referencian los componentes
        initListeners() //se ejecutan a los Listeners
        initUI() //para que aparezcan los datos que se modifican (altura,peso y edad)
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubstractWeight = findViewById(R.id.btnSubstractWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        btnSubstractAge = findViewById(R.id.btnSubstractAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        tvWeight = findViewById(R.id.tvWeight)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initListeners() {
        //pulsar hombre
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        //pulsar Mujer
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        //mostrar los cm
        rsHeight.addOnChangeListener { _, value, _ ->
            val decimalFormat = DecimalFormat("#.##") //se inutilizan los decimales
            currentHeight = decimalFormat.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }
        //modificar el peso
        btnAddWeight.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        btnSubstractWeight.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }

        //modificar la edad
        btnAddAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
        btnSubstractAge.setOnClickListener {
            currentAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener {
            navigateToResult(calculateIMC())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateIMC(): Double {
        val decimalFormat = DecimalFormat("#.##")
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        Log.i("lena", "el imc es $imc") //para ver en el logcat/command line lo que se va guardando
        return decimalFormat.format(imc).toDouble()
    }

    private fun navigateToResult(result: Double) {
        var intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun changeGender() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(selectedComponent: Boolean): Int {
        val colorReference = if (selectedComponent)
            R.color.background_component_selected
        else R.color.background_component
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() { //inicializar la funcion al empezar la activity
        setGenderColor()
        setWeight()
        setAge()
    }

}