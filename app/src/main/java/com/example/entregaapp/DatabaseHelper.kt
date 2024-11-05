import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "personagem.db"
        private const val DATABASE_VERSION = 1

        // Nome da tabela e colunas
        const val TABLE_PERSONAGEM = "personagens"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_RACA = "raca"
        const val COLUMN_CLASSE = "classe"
        const val COLUMN_FORCA = "forca"
        const val COLUMN_DESTREZA = "destreza"
        const val COLUMN_CONSTITUICAO = "constituicao"
        const val COLUMN_INTELIGENCIA = "inteligencia"
        const val COLUMN_SABEDORIA = "sabedoria"
        const val COLUMN_CARISMA = "carisma"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_PERSONAGEM (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT,
                $COLUMN_RACA TEXT,
                $COLUMN_CLASSE TEXT,
                $COLUMN_FORCA INTEGER,
                $COLUMN_DESTREZA INTEGER,
                $COLUMN_CONSTITUICAO INTEGER,
                $COLUMN_INTELIGENCIA INTEGER,
                $COLUMN_SABEDORIA INTEGER,
                $COLUMN_CARISMA INTEGER
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAGEM")
        onCreate(db)
    }

    // Inserir personagem
    fun inserirPersonagem(personagem: Personagem): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOME, personagem.nome)
            put(COLUMN_RACA, personagem.raca)
            put(COLUMN_FORCA, personagem.forca)
            put(COLUMN_DESTREZA, personagem.destreza)
            put(COLUMN_CONSTITUICAO, personagem.constituicao)
            put(COLUMN_INTELIGENCIA, personagem.inteligencia)
            put(COLUMN_SABEDORIA, personagem.sabedoria)
            put(COLUMN_CARISMA, personagem.carisma)
        }

        // Insere o personagem e retorna o ID da nova linha ou -1 em caso de erro
        val result = db.insert(TABLE_PERSONAGEM, null, contentValues)
        db.close() // Fecha o banco de dados após a operação
        return result
    }

    // Atualizar personagem
    fun atualizarPersonagem(personagem: Personagem): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nome", personagem.nome)
            put("raca", personagem.raca)
            put("forca", personagem.forca)
            put("destreza", personagem.destreza)
            put("constituicao", personagem.constituicao)
            put("inteligencia", personagem.inteligencia)
            put("sabedoria", personagem.sabedoria)
            put("carisma", personagem.carisma)
        }

        // Verifica se o ID não é nulo antes de tentar atualizar
        return if (personagem.id != null) {
            val rowsAffected = db.update("Personagem", values, "id = ?", arrayOf(personagem.id.toString()))
            db.close()
            rowsAffected > 0 // Retorna true se pelo menos uma linha foi atualizada
        } else {
            false
        }
    }

    // Deletar personagem
    fun deletarPersonagem(id: Int): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_PERSONAGEM, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }
    val modificador = ModificadorPadrao()

    // Buscar todos os personagens
    fun obterTodosPersonagens(): MutableList<Personagem> {
        val listaPersonagens = mutableListOf<Personagem>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PERSONAGEM, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val personagem = Personagem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                    raca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACA)),
                    forca = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FORCA)),
                    destreza = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESTREZA)),
                    constituicao = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUICAO)),
                    inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELIGENCIA)),
                    sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SABEDORIA)),
                    carisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARISMA)),
                    modificador = ModificadorPadrao()
                )
                listaPersonagens.add(personagem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPersonagens
    }

}
