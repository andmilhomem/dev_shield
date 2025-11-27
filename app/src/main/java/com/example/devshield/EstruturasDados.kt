package com.example.devshield

class Mapa(val jogador: Jogador, val adversario: Jogador) {
    init {
        jogador.associaMapa(this)
    }

    var mapaEnderecos: Array<Array<Endereco>> = Array(NUM_MAX_LINHAS) {
        Array(NUM_MAX_COLUNAS) { Endereco(-1, -1) }
    }

    // Preenche atributos linha e coluna dos endereços do mapa
    init {
        for (i in 0 until NUM_MAX_LINHAS) {
            for (j in 0 until NUM_MAX_COLUNAS) {
                mapaEnderecos[i][j].setLinhaColuna(i, j)
            }
        }
    }

    var numVirusRevelados = 0
    var numEnderecosEscondidos = NUM_MAX_LINHAS * NUM_MAX_COLUNAS
    var numBackupsRestantes = NUM_BACKUPS_INICIAL

    fun incrementaVirusRevelados() {
        numVirusRevelados++
    }

    fun decrementaBackups() {
        numBackupsRestantes--
    }

    fun revelaEndereco(linha: Int, coluna: Int) {
        // Obtém objeto correspondente
        val endereco = mapaEnderecos[linha][coluna]

        // Variável de controle para o caso de adjacentes não conterem vírus
        var adjacentesNaoContemVirus = true

        // Revela o endereço
        endereco.revelaEndereco()
        numEnderecosEscondidos--

        // Checa se endereco revelado contém vírus
        if (endereco.contemVirus()) {
            numVirusRevelados++
            numBackupsRestantes--
        } else {
            // Revela adjacentes e checa se contêm vírus
            adjacentesNaoContemVirus = revelaEnderecosAdjacentes(endereco)
        }
        val statusMapa = checaStatusMapa()

        // Jogador perdeu
        if (statusMapa == -1) {
            vencedor = adversario
            encerraJogo()
        }
        // Jogador venceu
        if (statusMapa == 1) {
            vencedor = jogador
            encerraJogo()
        }
        // Jogo continua
        if (adjacentesNaoContemVirus) {
            repeteInspecao()
        } else {
            mapaAtual = adversario.mapa // troca o mapa
            iniciaRodada()
        }
    }

    fun visitaEnderecoAdjacente(enderecoVisitado: Endereco): Boolean {
        var contemVirus = false

        enderecoVisitado.revelaEndereco()
        numEnderecosEscondidos--

        if (enderecoVisitado.contemVirus()) {
            numVirusRevelados++
            contemVirus = true
        }
        return contemVirus
    }

    fun revelaEnderecosAdjacentes(enderecoReferencia: Endereco): Boolean {
        val coluna = enderecoReferencia.coluna
        val linha = enderecoReferencia.linha
        var adjacentesContemVirus = false
        val colunaEsquerda = enderecoReferencia.coluna - 1
        val colunaDireita = enderecoReferencia.coluna + 1
        val linhaAcima = enderecoReferencia.linha - 1
        val linhaAbaixo = enderecoReferencia.linha + 1

        if (linhaAcima >= 0) { // Existe linha acima
            if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][coluna])) {
                adjacentesContemVirus = true
            }
            if (colunaEsquerda >= 0) { // Existe coluna à esquerda
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][colunaEsquerda])) {
                    adjacentesContemVirus = true
                }
                if (colunaDireita < NUM_MAX_COLUNAS) { // Existe coluna à direita
                    if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][colunaDireita])) {
                        adjacentesContemVirus = true
                    }
                }
            }
        }
        if (linhaAbaixo < NUM_MAX_LINHAS) { // Existe linha abaixo
            if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][coluna])) {
                adjacentesContemVirus = true
            }
            if (colunaEsquerda >= 0) { // Existe coluna à esquerda
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][colunaEsquerda])) {
                    adjacentesContemVirus = true
                }
                if (colunaDireita < NUM_MAX_COLUNAS) { // Existe coluna à direita
                    if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][colunaDireita])) {
                        adjacentesContemVirus = true
                    }
                }
            }
        }
        return adjacentesContemVirus
    }
    fun escondeEndereco(linha: Int, coluna: Int) {
        mapaEnderecos[linha][coluna].escondeEndereco()
    }
    fun checaStatusMapa(): Int {
        return when {
            numBackupsRestantes < 0 -> -1 // jogador perdeu
            numVirusRevelados == 5 -> 1   // jogador venceu
            else -> 0                    // jogo continua
        }
    }
}

class Endereco(var linha: Int, var coluna: Int) {
    var estaVisivel = true
    var idVirus = -1

    fun escondeEndereco() {
        estaVisivel = false
    }
    fun revelaEndereco() {
        estaVisivel = true
    }
    fun posicionaVirus(idVirusPosicionado: Int) {
        idVirus = idVirusPosicionado
    }
    fun contemVirus(): Boolean {
        return when {
            idVirus == -1 -> false
            else -> true
        }
    }
    fun setLinhaColuna(i: Int, j: Int) {
        linha = i
        coluna = j
    }
}

class Jogador(val id: Int, val nome: String) {
    lateinit var mapa: Mapa
    val pontuacao = 0
    val respostasErradas = mutableListOf<Questao>()

    fun associaMapa(mapaAssociado: Mapa) {
        mapa = mapaAssociado
    }
}

class Questao(val id: Int, val titulo: String, val situacao: String, val boaConduta: String, val maConduta: String, val feedbackMaConduta: String) {
}