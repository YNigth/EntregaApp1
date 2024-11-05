package com.example.entregaapp

import Modificador
import ModificadorPadrao
import Personagem
import android.os.Bundle
import DatabaseHelper
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.entregaapp.databinding.ActivityMainBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowCompat
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import BonusRacialHumano
import BonusRacialAltoElfo
import BonusRacialAnao
import BonusRacialAnaoDaColina
import BonusRacialAnaoDaMontanha
import BonusRacialDracono
import BonusRacialDrow
import BonusRacialElfo
import BonusRacialElfoDaFloresta
import BonusRacialGnomo
import BonusRacialGnomoDasRochas
import BonusRacialGnomoDaFloresta
import BonusRacialHalfling
import BonusRacialHalflingPesLeves
import BonusRacialHalflingRobusto
import BonusRacialMeioElfo
import BonusRacialOrc
import BonusRacialTiefling
import android.widget.Toast
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    var pontosDisponiveis = 27 // Valor inicial dos pontos disponíveis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ativa o modo edge-to-edge
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configura o layout para responder a system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun atualizarPontosDisponiveis() {
            try {
                // Captura os valores de cada campo com valores padrão de 8, caso a entrada seja inválida
                val nome = findViewById<EditText>(R.id.etNome).text.toString()
                val raca = findViewById<Spinner>(R.id.etRaca).selectedItem.toString()
                val forca = findViewById<EditText>(R.id.etForca).text.toString().toIntOrNull() ?: 8
                val destreza =
                    findViewById<EditText>(R.id.etDestreza).text.toString().toIntOrNull() ?: 8
                val constituicao =
                    findViewById<EditText>(R.id.etConstituicao).text.toString().toIntOrNull() ?: 8
                val inteligencia =
                    findViewById<EditText>(R.id.etInteligencia).text.toString().toIntOrNull() ?: 8
                val sabedoria =
                    findViewById<EditText>(R.id.etSabedoria).text.toString().toIntOrNull() ?: 8
                val carisma =
                    findViewById<EditText>(R.id.etCarisma).text.toString().toIntOrNull() ?: 8

                val modificadorCalculo = ModificadorPadrao()

                val personagem = Personagem(
                    nome = nome, // deve ser String
                    raca = raca, // deve ser String
                    forca = forca, // deve ser Int
                    destreza = destreza, // deve ser Int
                    constituicao = constituicao, // deve ser Int
                    inteligencia = inteligencia, // deve ser Int
                    sabedoria = sabedoria, // deve ser Int
                    carisma = carisma, // deve ser Int
                    modificador = ModificadorPadrao(), // deve ser do tipo Modificador
                    bonusRacialStrategy = null // ou uma instância válida
                )

                // Calcula os custos para cada atributo
                val custoForca = personagem.calcularCusto(forca)
                val custoDestreza = personagem.calcularCusto(destreza)
                val custoConstituicao = personagem.calcularCusto(constituicao)
                val custoInteligencia = personagem.calcularCusto(inteligencia)
                val custoSabedoria = personagem.calcularCusto(sabedoria)
                val custoCarisma = personagem.calcularCusto(carisma)

                // Custo total dos atributos
                val custoTotal =
                    custoForca + custoDestreza + custoConstituicao + custoInteligencia + custoSabedoria + custoCarisma

                // Calcula os pontos restantes (27 é o número total inicial de pontos disponíveis)
                val pontosRestantes = 27 - custoTotal

                // Atualiza o TextView com os pontos restantes
                findViewById<TextView>(R.id.tvPontosDisponiveis).text =
                    "Pontos Disponíveis: $pontosRestantes"

                // Atualiza o valor global de pontos disponíveis
                pontosDisponiveis = pontosRestantes

                // Valida se os pontos restantes são negativos
                if (pontosRestantes < 0) {
                    findViewById<TextView>(R.id.tvPontosDisponiveis).text = "Pontos insuficientes!"
                }

            } catch (e: NumberFormatException) {
                // Tratamento para caso ocorra algum erro durante a conversão de texto para número
                findViewById<TextView>(R.id.tvPontosDisponiveis).text = "Erro ao calcular pontos"
            }
        }

        fun configurarTextWatchers() {
            val camposAtributos = listOf(
                findViewById<EditText>(R.id.etForca),
                findViewById<EditText>(R.id.etDestreza),
                findViewById<EditText>(R.id.etConstituicao),
                findViewById<EditText>(R.id.etInteligencia),
                findViewById<EditText>(R.id.etSabedoria),
                findViewById<EditText>(R.id.etCarisma)
            )

            // Adiciona o TextWatcher para cada campo de atributo
            for (campo in camposAtributos) {
                campo.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        // Atualiza os pontos disponíveis sempre que o valor de um campo muda
                        atualizarPontosDisponiveis()
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }

        fun aplicarBonusRacial(personagem: Personagem) {
            when (personagem.raca) {
                "Humano" -> personagem.bonusRacialStrategy = BonusRacialHumano()
                "Alto Elfo" -> personagem.bonusRacialStrategy = BonusRacialAltoElfo()
                "Anão" -> personagem.bonusRacialStrategy = BonusRacialAnao()
                "Anão da Colina" -> personagem.bonusRacialStrategy = BonusRacialAnaoDaColina()
                "Anão da Montanha" -> personagem.bonusRacialStrategy = BonusRacialAnaoDaMontanha()
                "Draconato" -> personagem.bonusRacialStrategy = BonusRacialDracono()
                "Drow" -> personagem.bonusRacialStrategy = BonusRacialDrow()
                "Elfo" -> personagem.bonusRacialStrategy = BonusRacialElfo()
                "Elfo da Floresta" -> personagem.bonusRacialStrategy = BonusRacialElfoDaFloresta()
                "Gnomo" -> personagem.bonusRacialStrategy = BonusRacialGnomo()
                "Gnomo das Rochas" -> personagem.bonusRacialStrategy = BonusRacialGnomoDasRochas()
                "Gnomo da Floresta" -> personagem.bonusRacialStrategy = BonusRacialGnomoDaFloresta()
                "Halfling" -> personagem.bonusRacialStrategy = BonusRacialHalfling()
                "Halfling Pés-Leves" -> personagem.bonusRacialStrategy =
                    BonusRacialHalflingPesLeves()

                "Halfling Robusto" -> personagem.bonusRacialStrategy = BonusRacialHalflingRobusto()
                "Meio-Elfo" -> personagem.bonusRacialStrategy = BonusRacialMeioElfo()
                "Orc" -> personagem.bonusRacialStrategy = BonusRacialOrc()
                "Tiefling" -> personagem.bonusRacialStrategy = BonusRacialTiefling()
            }
        }

        configurarTextWatchers()

        // Mapeando o botão btnCreateCharacter corretamente
        val btnCreateCharacter = findViewById<Button>(R.id.btnCreateCharacter)
        btnCreateCharacter.setOnClickListener {

            // Captura os valores dos campos
            val nome = findViewById<EditText>(R.id.etNome).text.toString()
            val raca = findViewById<Spinner>(R.id.etRaca).selectedItem.toString()

            try {
                val forca = findViewById<EditText>(R.id.etForca).text.toString().toInt()
                val destreza = findViewById<EditText>(R.id.etDestreza).text.toString().toInt()
                val constituicao =
                    findViewById<EditText>(R.id.etConstituicao).text.toString().toInt()
                val inteligencia =
                    findViewById<EditText>(R.id.etInteligencia).text.toString().toInt()
                val sabedoria = findViewById<EditText>(R.id.etSabedoria).text.toString().toInt()
                val carisma = findViewById<EditText>(R.id.etCarisma).text.toString().toInt()

                val modificador = ModificadorPadrao()

                // Verifica se os inputs são válidos usando a função da classe Personagem
                val personagem = Personagem(
                    nome = nome, // deve ser String
                    raca = raca, // deve ser String
                    forca = forca, // deve ser Int
                    destreza = destreza, // deve ser Int
                    constituicao = constituicao, // deve ser Int
                    inteligencia = inteligencia, // deve ser Int
                    sabedoria = sabedoria, // deve ser Int
                    carisma = carisma, // deve ser Int
                    modificador = ModificadorPadrao(), // deve ser do tipo Modificador
                    bonusRacialStrategy = null // ou uma instância válida
                )

                if (!personagem.validarAtributosPersonagem(
                        nome,
                        forca,
                        destreza,
                        constituicao,
                        inteligencia,
                        sabedoria,
                        carisma
                    )
                ) {
                    Toast.makeText(
                        this,
                        "Preencha todos os campos corretamente. Atributos devem estar entre 8 e 15.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                // Soma dos custos
                val custoTotal = personagem.calcularCusto(forca) +
                        personagem.calcularCusto(destreza) +
                        personagem.calcularCusto(constituicao) +
                        personagem.calcularCusto(inteligencia) +
                        personagem.calcularCusto(sabedoria) +
                        personagem.calcularCusto(carisma)

                // Verifica se o custo total excede os pontos disponíveis
                if (custoTotal > 27) {
                    Toast.makeText(
                        this,
                        "Pontos insuficientes para distribuir esses atributos. Custo: $custoTotal, Disponíveis: $pontosDisponiveis",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                // Aplica o bônus racial
                aplicarBonusRacial(personagem)
                personagem.aplicarBonusRacial()

                // Após aplicar bônus raciais e antes de iniciar a nova atividade:
                val db = DatabaseHelper(this)
                val resultadoId = db.inserirPersonagem(personagem)

                if (resultadoId == -1L) {
                    Toast.makeText(this, "Erro ao salvar o personagem", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }



                // Atualiza os valores finais dos atributos com bônus raciais aplicados
                val forcaFinal = personagem.forca
                val destrezaFinal = personagem.destreza
                val constituicaoFinal = personagem.constituicao
                val inteligenciaFinal = personagem.inteligencia
                val sabedoriaFinal = personagem.sabedoria
                val carismaFinal = personagem.carisma

                // Envia os dados do personagem para a segunda tela
                val intent = Intent(this, telaDois::class.java)
                intent.putExtra("NOME", nome)
                intent.putExtra("RACA", raca)
                intent.putExtra("FORCA", forcaFinal)
                intent.putExtra("DESTREZA", destrezaFinal)
                intent.putExtra("CONSTITUICAO", constituicaoFinal)
                intent.putExtra("INTELIGENCIA", inteligenciaFinal)
                intent.putExtra("SABEDORIA", sabedoriaFinal)
                intent.putExtra("CARISMA", carismaFinal)
                intent.putExtra("PONTOS_DE_VIDA", modificador.calcularMod(constituicaoFinal) + 10)

                startActivity(intent)

            } catch (e: NumberFormatException) {
                // Exibe um aviso se os valores forem inválidos
                Toast.makeText(
                    this,
                    "Preencha todos os campos corretamente com números válidos.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

}