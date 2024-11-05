package com.example.entregaapp

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import Personagem
import ModificadorPadrao
import android.widget.Spinner

class EditarPersonagemActivity : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextRaca: Spinner
    private lateinit var editTextForca: EditText
    private lateinit var editTextDestreza: EditText
    private lateinit var editTextConstituicao: EditText
    private lateinit var editTextInteligencia: EditText
    private lateinit var editTextSabedoria: EditText
    private lateinit var editTextCarisma: EditText
    private lateinit var databaseHelper: DatabaseHelper
    private var personagemId: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_personagem)

        editTextNome = findViewById(R.id.editTextNome)
        editTextRaca = findViewById(R.id.etRaca)
        editTextForca = findViewById(R.id.editTextForca)
        editTextDestreza = findViewById(R.id.editTextDestreza)
        editTextConstituicao = findViewById(R.id.editTextConstituicao)
        editTextInteligencia = findViewById(R.id.editTextInteligencia)
        editTextSabedoria = findViewById(R.id.editTextSabedoria)
        editTextCarisma = findViewById(R.id.editTextCarisma)

        databaseHelper = DatabaseHelper(this)

        // Configuração para receber valores
        personagemId = intent.getIntExtra("PERSONAGEM_ID", -1)
        editTextNome.setText(intent.getStringExtra("NOME"))

        // Configurar valor do Spinner (raca)
        val racaSelecionada = intent.getStringExtra("RACA")
        val racasArray = resources.getStringArray(R.array.race_array)
        val racaIndex = racasArray.indexOf(racaSelecionada)
        if (racaIndex >= 0) {
            editTextRaca.setSelection(racaIndex)
        }

        // Demais configurações para os EditTexts
        editTextForca.setText(intent.getIntExtra("FORCA", 0).toString())
        editTextDestreza.setText(intent.getIntExtra("DESTREZA", 0).toString())
        editTextConstituicao.setText(intent.getIntExtra("CONSTITUICAO", 0).toString())
        editTextInteligencia.setText(intent.getIntExtra("INTELIGENCIA", 0).toString())
        editTextSabedoria.setText(intent.getIntExtra("SABEDORIA", 0).toString())
        editTextCarisma.setText(intent.getIntExtra("CARISMA", 0).toString())

        findViewById<Button>(R.id.btnSalvar).setOnClickListener {
            val updatedPersonagem = Personagem(
                id = personagemId,
                nome = editTextNome.text.toString(),
                raca = editTextRaca.selectedItem.toString(), // Atualizado para o Spinner
                forca = editTextForca.text.toString().toIntOrNull() ?: 0,
                destreza = editTextDestreza.text.toString().toIntOrNull() ?: 0,
                constituicao = editTextConstituicao.text.toString().toIntOrNull() ?: 0,
                inteligencia = editTextInteligencia.text.toString().toIntOrNull() ?: 0,
                sabedoria = editTextSabedoria.text.toString().toIntOrNull() ?: 0,
                carisma = editTextCarisma.text.toString().toIntOrNull() ?: 0,
                modificador = ModificadorPadrao()
            )

            if (personagemId != null && personagemId != -1) {
                val sucesso = databaseHelper.atualizarPersonagem(updatedPersonagem)
                if (sucesso) {
                    Toast.makeText(this, "Personagem atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao atualizar personagem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "ID do personagem inválido", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnVoltar).setOnClickListener {
            // Voltar para a tela anterior
            finish()
        }
    }
}