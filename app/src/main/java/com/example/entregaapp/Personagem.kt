import com.example.entregaapp.PersonagemInterface


open class Personagem(
    val id: Int? = null,
    val nome: String,
    var raca: String,
    var forca: Int,
    var destreza: Int,
    var constituicao: Int,
    var inteligencia: Int,
    var sabedoria: Int,
    var carisma: Int,
    var modificador: Modificador,
    var bonusRacialStrategy: BonusRacialStrategy? = null
) : PersonagemInterface {

    override fun toString(): String {
        return nome // Retorna o nome quando convertido para string
    }

    init {
        definirBonusRacial()
        aplicarBonusRacial() // Aplicar o bônus racial ao ser criado
    }


    // Implemente o método calcularMod, exigido pela interface
    override fun calcularMod(valor: Int): Int {
        return when {
            valor >= 10 -> (valor - 10) / 2
            else -> (valor - 10) / 2 - 1
        }
    }

    // Outros métodos da classe
    fun calcularCusto(pontos: Int): Int {
        return when (pontos) {
            8 -> 0
            9 -> 1
            10 -> 2
            11 -> 3
            12 -> 4
            13 -> 5
            14 -> 7
            15 -> 9
            else -> 0
        }
    }

    fun validarAtributosPersonagem(
        nome: String,
        forca: Int,
        destreza: Int,
        constituicao: Int,
        inteligencia: Int,
        sabedoria: Int,
        carisma: Int
    ): Boolean {
        if (nome.isBlank()) {
            println("Nome inválido. O nome não pode estar vazio.")
            return false
        }

        val atributosValidos = listOf(
            "Força" to forca,
            "Destreza" to destreza,
            "Constituição" to constituicao,
            "Inteligência" to inteligencia,
            "Sabedoria" to sabedoria,
            "Carisma" to carisma
        )

        for ((atributo, valor) in atributosValidos) {
            if (valor !in 8..15) {
                println("Valor inválido para $atributo. O valor deve estar entre 8 e 15.")
                return false
            }
        }

        return true
    }

    fun definirBonusRacial() {
        bonusRacialStrategy = when (raca) {
            "Humano" -> BonusRacialHumano()
            "Anão" -> BonusRacialAnao()
            "Anão da Colina" -> BonusRacialAnaoDaColina()
            "Anão da Montanha" -> BonusRacialAnaoDaMontanha()
            "Draconato" -> BonusRacialDracono()
            "Drow" -> BonusRacialDrow()
            "Elfo" -> BonusRacialElfo()
            "Elfo da Floresta" -> BonusRacialElfoDaFloresta()
            "Gnomo" -> BonusRacialGnomo()
            "Gnomo da Floresta" -> BonusRacialGnomoDaFloresta()
            "Gnomo das Rochas" -> BonusRacialGnomoDasRochas()
            "Halfling" -> BonusRacialHalfling()
            "Halfling Pés-Leves" -> BonusRacialHalflingPesLeves()
            "Halfling Robusto" -> BonusRacialHalflingRobusto()
            "Meio-Elfo" -> BonusRacialMeioElfo()
            "Orc" -> BonusRacialOrc()
            "Tiefling" -> BonusRacialTiefling()
            else -> null
        }
    }

    override fun aplicarBonusRacial() {
        bonusRacialStrategy?.aplicarBonus(this)
    }

    override fun mostrar() {
        println("NOME : $nome")
        println("RAÇA : $raca")
        println("FORÇA : $forca")
        println("DESTREZA : $destreza")
        println("CONSTITUIÇÃO : $constituicao")
        println("INTELIGÊNCIA : $inteligencia")
        println("SABEDORIA : $sabedoria")
        println("CARISMA : $carisma")
        println("PONTOS DE VIDA : ${modificador.calcularMod(constituicao) + 10}")
    }

    fun bonus(): Map<String, Int> {
        val bonus = mutableMapOf<String, Int>()

        when (raca) {
            "Humano" -> {
                forca += 1
                destreza += 1
                constituicao += 1
                inteligencia += 1
                sabedoria += 1
                carisma += 1

                bonus["forca"] = 1
                bonus["destreza"] = 1
                bonus["constituicao"] = 1
                bonus["inteligencia"] = 1
                bonus["sabedoria"] = 1
                bonus["carisma"] = 1
            }
            "Alto Elfo" -> {
                inteligencia += 1
                bonus["inteligencia"] = 1
            }
            // Continue com as outras raças...
        }

        return bonus
    }
}
