interface BonusRacialStrategy {
    fun aplicarBonus(personagem: Personagem)
}

class BonusRacialHumano : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 1
        personagem.destreza += 1
        personagem.constituicao += 1
        personagem.inteligencia += 1
        personagem.sabedoria += 1
        personagem.carisma += 1
    }
}

class BonusRacialAltoElfo : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.inteligencia += 1
    }
}

class BonusRacialAnao : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 2
        personagem.inteligencia += 1
    }
}

class BonusRacialAnaoDaColina : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.sabedoria += 1
    }
}

class BonusRacialAnaoDaMontanha : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
    }
}

class BonusRacialDracono : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
        personagem.carisma += 1
    }
}
class BonusRacialDrow : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.carisma += 1
    }
}

class BonusRacialElfo : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.destreza += 2
    }
}

class BonusRacialElfoDaFloresta : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.sabedoria += 1
    }
}

class BonusRacialGnomo : BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {

    }
}

class BonusRacialGnomoDaFloresta: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.destreza += 2
    }
}

class BonusRacialGnomoDasRochas: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 1
    }
}

class BonusRacialHalfling: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.destreza += 2
    }
}

class BonusRacialHalflingPesLeves: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.carisma += 1
    }
}

class BonusRacialHalflingRobusto: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.constituicao += 1
    }
}

class BonusRacialMeioElfo: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.carisma += 2
    }
}

class BonusRacialOrc: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.forca += 2
    }
}

class BonusRacialTiefling: BonusRacialStrategy {
    override fun aplicarBonus(personagem: Personagem) {
        personagem.inteligencia += 1
        personagem.carisma += 2
    }
}