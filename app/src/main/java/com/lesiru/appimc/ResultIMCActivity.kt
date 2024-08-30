package com.lesiru.appimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lesiru.appimc.R
import com.lesiru.appimc.IMCActivity.Companion.IMC_KEY

class ResultIMCActivity : AppCompatActivity() {

    //atributos
    private lateinit var tvIMC: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnReCalculate: Button
    private var imc: Double = 20.0

    //metodos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        initComponents()
        var result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btnReCalculate.setOnClickListener {
            onBackPressed()
            /*var intent = Intent(this,IMCActivity::class.java)
            startActivity(intent)*/
        }
    }

    private fun initComponents() {
        tvIMC = findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescription = findViewById(R.id.tvDescription)
        btnReCalculate = findViewById(R.id.btnReCalculate)
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()
        when (result) {
            in 0.00..18.50 -> { //bajo peso
                tvResult.text = getString(R.string.title_bajo)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.bajo))
                tvDescription.text = getString(R.string.description_bajo)
            }
            in 18.51 .. 24.99 -> { //peso normal
                tvResult.text = getString(R.string.title_normal)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.normal))
                tvDescription.text = getString(R.string.description_normal)
            }
            in 25.00 .. 29.99 -> { //sobrepeso
                tvResult.text = getString(R.string.title_sobrepeso)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.sobrepeso))
                tvDescription.text = getString(R.string.description_sobrepeso)
            }
            in 30.0 .. 99.0 -> { //obesidad
                tvResult.text = getString(R.string.title_obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
                tvDescription.text = getString(R.string.description_obesidad)
            }
            else -> { //error
                tvResult.text = R.string.error.toString()
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
                tvIMC.text = R.string.error.toString()
                tvIMC.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
                tvDescription.text = R.string.error.toString()
                tvDescription.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
            }
        }
    }

}