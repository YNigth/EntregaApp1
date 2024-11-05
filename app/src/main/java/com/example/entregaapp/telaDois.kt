package com.example.entregaapp

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Personagem

class telaDois : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listViewPersonagens: ListView
    private lateinit var adapter: PersonagemAdapter
    private var personagens: MutableList<Personagem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_dois)

        // Configuração para bordas da tela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.telaDois)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)
        listViewPersonagens = findViewById(R.id.listViewPersonagens)

        // Carrega personagens do banco de dados e configura o adaptador
        carregarPersonagens()

        // Configura o botão de Criar Novo
        findViewById<Button>(R.id.btnCriarNovo).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Configura o botão de Excluir
        findViewById<Button>(R.id.btnExcluir).setOnClickListener {
            val posicaoSelecionada = listViewPersonagens.checkedItemPosition
            if (posicaoSelecionada >= 0) {
                val personagemSelecionado = personagens[posicaoSelecionada]
                personagemSelecionado.id?.let { id ->
                    databaseHelper.deletarPersonagem(id)
                    Toast.makeText(this, "${personagemSelecionado.nome} excluído com sucesso", Toast.LENGTH_SHORT).show()
                    carregarPersonagens() // Atualiza a lista
                }
            } else {
                Toast.makeText(this, "Selecione um personagem para excluir", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o botão de Editar
        findViewById<Button>(R.id.btnEditar).setOnClickListener {
            val posicaoSelecionada = listViewPersonagens.checkedItemPosition
            if (posicaoSelecionada >= 0) {
                val personagemSelecionado = personagens[posicaoSelecionada]

                // Inicia a tela de edição e passa os dados do personagem
                val intent = Intent(this, EditarPersonagemActivity::class.java).apply {
                    putExtra("ID", personagemSelecionado.id)
                    putExtra("NOME", personagemSelecionado.nome)
                    putExtra("RACA", personagemSelecionado.raca) // Adicionei a raça, caso esteja implementada
                    putExtra("FORCA", personagemSelecionado.forca)
                    putExtra("DESTREZA", personagemSelecionado.destreza)
                    putExtra("CONSTITUICAO", personagemSelecionado.constituicao)
                    putExtra("INTELIGENCIA", personagemSelecionado.inteligencia)
                    putExtra("SABEDORIA", personagemSelecionado.sabedoria)
                    putExtra("CARISMA", personagemSelecionado.carisma)
                }
                startActivityForResult(intent, EDITAR_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Selecione um personagem para editar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun carregarPersonagens() {
        personagens = databaseHelper.obterTodosPersonagens()
        adapter = PersonagemAdapter(this, personagens)
        listViewPersonagens.adapter = adapter
        listViewPersonagens.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

    companion object {
        private const val EDITAR_REQUEST_CODE = 100 // Código para identificar a requisição de edição
    }
}
