package com.example.devshield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

fun reiniciaJogo(vm: MainViewModel) {
    // Retorna variáveis de ViewModel aos valores iniciais
    vm.jogadorAtual = 1
    vm.jogo_comecou = false
    vm.listaQuestoesAtual = vm.listaQuestoesJSON.toMutableList()
    vm.jogador1 = Jogador(1,"")
    vm.jogador2 = Jogador(2, "")
    vm.mapaJogador1 = Mapa(vm.jogador1, vm.jogador2, vm)
    vm.mapaJogador2 = Mapa(vm.jogador2, vm.jogador1, vm)

    vm.telaAtual = Tela.NOVO_JOGO
}

fun editaMapa(vm: MainViewModel) {
    vm.telaAtual = Tela.NOVO_MAPA
}
fun iniciaNovaRodada(vm: MainViewModel) { // Supõe que jogadorAtual está atualizado

    // Primeira rodada do jogo
    if (vm.jogo_comecou == false) {
        // Esconde todos os endereços de ambos os mapas
        for (i in 0 until NUM_MAX_LINHAS) {
            for (j in 0 until NUM_MAX_COLUNAS) {
                vm.mapaJogador1.mapaEnderecos[i][j].escondeEndereco()
                vm.mapaJogador2.mapaEnderecos[i][j].escondeEndereco()
            }
        }
        vm.jogo_comecou = true
    }

    // Checa se ainda há questões
    if (vm.listaQuestoesAtual.isEmpty()) {
        vm.listaQuestoesAtual = vm.listaQuestoesJSON.toMutableList()
        for (i in 0 until vm.listaQuestoesAtual.size) {
            vm.listaQuestoesAtual[i].condutasDisponiveis = mutableListOf(Conduta.BOA, Conduta.MA)
        }
    }

    // Escolhe questão
    val questao = vm.listaQuestoesAtual.random()
    vm.questaoAtual = questao
    val conduta = questao.condutasDisponiveis.random()

    // Atualiza banco de questões
    questao.utilizaConduta(conduta)
    if (questao.condutasDisponiveis.isEmpty()) {
        vm.listaQuestoesAtual.remove(questao)
    }

    // Atualiza ViewModel
    vm.situacaoAtual = questao.situacao
    var textoConduta: String
    var tipoConduta = Conduta.BOA
    if (conduta == Conduta.BOA) textoConduta = questao.boaConduta
    else {
        tipoConduta = Conduta.MA
        textoConduta = questao.maConduta
    }
    vm.textoCondutaAtual = textoConduta
    vm.tipoCondutaAtual = tipoConduta

    // Troca a tela
    vm.telaAtual = Tela.SITUACAO
}

fun exibeConduta(vm: MainViewModel) {
    vm.telaAtual = Tela.CONDUTA
}

fun exibeEfeitoConduta(vm: MainViewModel, avaliacaoJogador: Conduta) {
    // Identifica jogador
    val jogador: Jogador
    if (vm.jogadorAtual == 1) jogador = vm.jogador1
    else jogador = vm.jogador2

    // Jogador acertou
    if (avaliacaoJogador == vm.tipoCondutaAtual) {
        vm.desempenhoJogadaAtual = DesempenhoJogada.ACERTOU
        incrementaPontuacao(jogador)
        jogador.mapa.numBackupsRestantes++
    }
    // Jogador errou
    else {
        jogador.respostasErradas.add(vm.questaoAtual)
        if (vm.tipoCondutaAtual == Conduta.BOA)
            vm.desempenhoJogadaAtual = DesempenhoJogada.ERROU_CONDUTA_E_BOA
        else
            vm.desempenhoJogadaAtual = DesempenhoJogada.ERROU_CONDUTA_E_MA
    }
    vm.telaAtual = Tela.EFEITO_CONDUTA
}
fun exibeMapa(vm: MainViewModel) {
    vm.resultadoInspecaoAtual = ResultadoInspecao.PENDENTE
    vm.telaAtual = Tela.INSPECAO
}

fun inspecionaEndereco(vm: MainViewModel, linha: Int, coluna: Int)  {
    // Identifica objeto do mapa atual
    var mapaAtual: Mapa
    if (vm.jogadorAtual == 1) mapaAtual = vm.mapaJogador1
    else mapaAtual = vm.mapaJogador2

    // Revela endereço indicado
    mapaAtual.revelaEndereco(linha, coluna)
}

fun encerraJogo(vm: MainViewModel, vencedor: Int) {
    vm.vencedor = vencedor
    vm.jogadorAtual = 1
    vm.telaAtual = Tela.ENCERRAMENTO
}

fun exibeFeedback(vm: MainViewModel, jogador: Int) {
    vm.jogadorAtual = jogador
    vm.telaAtual = Tela.FEEDBACK
}
fun incrementaPontuacao(jogador: Jogador) {
    jogador.pontuacao++
}