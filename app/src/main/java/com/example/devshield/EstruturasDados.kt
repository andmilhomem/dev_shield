package com.example.devshield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Tela { NOVO_JOGO, NOVO_MAPA, SITUACAO, CONDUTA, EFEITO_CONDUTA, INSPECAO, ENCERRAMENTO, FEEDBACK }

enum class Conduta { BOA, MA }

enum class DesempenhoJogada { ACERTOU, ERROU_CONDUTA_E_BOA, ERROU_CONDUTA_E_MA }
enum class ResultadoInspecao { PENDENTE, VIRUS, NAO_VIRUS, REPETE_JOGADA, ACABOU_MEMORIA, REVELOU_TUDO }

class Mapa(val jogador: Jogador, val adversario: Jogador, var vm : MainViewModel) {
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

    var mapaColunaVirus = mutableStateListOf(0, 1, 2, 3, 4)

    var numVirusPosicionados by mutableStateOf(0)
    var numVirusRevelados by mutableStateOf(0)
    var numBackupsRestantes by mutableStateOf(NUM_BACKUPS_INICIAL)

    fun utilizaVirus(idVirus: Int) {
        this.numVirusPosicionados++
        this.mapaColunaVirus[idVirus] = -1
    }
    fun revelaEndereco(linha: Int, coluna: Int) {
        // Obtém objeto correspondente
        val endereco = mapaEnderecos[linha][coluna]

        // Variáveis de controle para o caso de adjacentes não conterem vírus
        var adjacentesNaoContemVirus = true

        // Revela o endereço
        endereco.revelaEndereco()

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
        if (statusMapa == -1) vm.resultadoInspecaoAtual = ResultadoInspecao.ACABOU_MEMORIA
        // Jogador venceu
        else if (statusMapa == 1) vm.resultadoInspecaoAtual = ResultadoInspecao.REVELOU_TUDO
        // Repete jogada
        else if (endereco.contemVirus() == false && adjacentesNaoContemVirus) vm.resultadoInspecaoAtual = ResultadoInspecao.REPETE_JOGADA
        // Era vírus
        else if (endereco.contemVirus()) vm.resultadoInspecaoAtual = ResultadoInspecao.VIRUS
        // Não era vírus
        else vm.resultadoInspecaoAtual = ResultadoInspecao.NAO_VIRUS
    }

    fun visitaEnderecoAdjacente(enderecoVisitado: Endereco): Boolean {
        if (enderecoVisitado.estaVisivel) {
            return false
        }
        var contemVirus = false

        enderecoVisitado.revelaEndereco()

        if (enderecoVisitado.contemVirus()) {
            numVirusRevelados++
            contemVirus = true
        }
        return contemVirus
    }

    fun revelaEnderecosAdjacentes(enderecoReferencia: Endereco): Boolean {
        val coluna = enderecoReferencia.coluna
        val linha = enderecoReferencia.linha
        var adjacentesNaoContemVirus = true
        val colunaEsquerda = coluna - 1
        val colunaDireita = coluna + 1
        val linhaAcima = linha - 1
        val linhaAbaixo = linha + 1

        // Visita mesma linha
        if (colunaEsquerda >= 0) { // Existe coluna à esquerda
            if (visitaEnderecoAdjacente(mapaEnderecos[linha][colunaEsquerda])) {
                adjacentesNaoContemVirus = false
            }
        }
        if (colunaDireita < NUM_MAX_COLUNAS) { // Existe coluna à direita
            if (visitaEnderecoAdjacente(mapaEnderecos[linha][colunaDireita])) {
                adjacentesNaoContemVirus = false
            }
        }

        // Visita linha acima
        if (linhaAcima >= 0) { // Existe linha acima
            if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][coluna])) {
                adjacentesNaoContemVirus = false
            }
            if (colunaEsquerda >= 0) { // Existe coluna à esquerda
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][colunaEsquerda])) {
                    adjacentesNaoContemVirus = false
                }
            }
            if (colunaDireita < NUM_MAX_COLUNAS) { // Existe coluna à direita
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAcima][colunaDireita])) {
                    adjacentesNaoContemVirus = false
                }
            }
        }

        // Visita linha abaixo
        if (linhaAbaixo < NUM_MAX_LINHAS) { // Existe linha abaixo
            if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][coluna])) {
                adjacentesNaoContemVirus = false
            }
            if (colunaEsquerda >= 0) { // Existe coluna à esquerda
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][colunaEsquerda])) {
                    adjacentesNaoContemVirus = false
                }
            }
            if (colunaDireita < NUM_MAX_COLUNAS) { // Existe coluna à direita
                if (visitaEnderecoAdjacente(mapaEnderecos[linhaAbaixo][colunaDireita])) {
                    adjacentesNaoContemVirus = false
                }
            }
        }

        return adjacentesNaoContemVirus
    }
    fun checaStatusMapa(): Int {
        return when {
            numBackupsRestantes < 0 -> -1 // jogador perdeu
            numVirusRevelados == NUM_MAX_VIRUS_POSICIONAVEIS -> 1   // jogador venceu
            else -> 0                    // jogo continua
        }
    }
}

class Endereco(var linha: Int, var coluna: Int) {
    var estaVisivel by mutableStateOf(true)
    var idVirus by mutableStateOf(-1)

    fun escondeEndereco() {
        this.estaVisivel = false
    }
    fun revelaEndereco() {
        this.estaVisivel = true
    }
    fun posicionaVirus(idVirusPosicionado: Int) {
        idVirus = idVirusPosicionado
    }
    fun recebeVirus(idVirus: Int, mapa: Mapa) {
        this.idVirus = idVirus
        mapa.utilizaVirus(idVirus)
    }
    fun perdeVirus() {
        this.idVirus = -1
    }
    fun contemVirus(): Boolean {
        return when {
            this.idVirus == -1 -> false
            else -> true
        }
    }
    fun setLinhaColuna(i: Int, j: Int) {
        this.linha = i
        this.coluna = j
    }
}

class Jogador(val id: Int, var nome: String) {
    lateinit var mapa: Mapa
    var pontuacao by mutableStateOf(0)
    var respostasErradas = mutableListOf<Questao>()

    fun associaMapa(mapaAssociado: Mapa) {
        mapa = mapaAssociado
    }
}

class Questao(val id: Int,
              val titulo: String,
              val situacao: String,
              val boaConduta: String,
              val maConduta: String,
              val efeitoMaConduta: String,
              var condutasDisponiveis: MutableList<Conduta> = mutableListOf(Conduta.BOA, Conduta.MA)) {

    fun utilizaConduta(conduta: Conduta) {
        condutasDisponiveis.remove(conduta)
    }
}