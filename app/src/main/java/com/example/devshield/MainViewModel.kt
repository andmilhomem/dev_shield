package com.example.devshield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

// Classe que declara e manipula variáveis
class MainViewModel : ViewModel() {
    var telaAtual by mutableStateOf(Tela.NOVO_JOGO)
    var jogadorAtual by mutableStateOf(1)
    var situacaoAtual by mutableStateOf("")
    var textoCondutaAtual by mutableStateOf("")
    var tipoCondutaAtual by mutableStateOf( Conduta.BOA)
    var desempenhoJogadaAtual by mutableStateOf( DesempenhoJogada.ACERTOU)
    lateinit var questaoAtual: Questao

    var jogo_comecou by mutableStateOf(false)
    var resultadoInspecaoAtual by mutableStateOf(ResultadoInspecao.PENDENTE)

    lateinit var listaQuestoesJSON : List<Questao>
    lateinit var listaQuestoesAtual : MutableList<Questao>

    var vencedor by mutableStateOf( -1)

    var jogador1 = Jogador(1,"")
    var jogador2 = Jogador(2, "")
    var mapaJogador1 = Mapa(jogador1, jogador2, this)
    var mapaJogador2 = Mapa(jogador2, jogador1, this)

    fun carregarQuestoes(context: Context) {
        val inputStream = context.resources.openRawResource(R.raw.questoes)  // Acessa o arquivo no raw
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val type = object : TypeToken<List<Questao>>() {}.type
        listaQuestoesJSON = gson.fromJson(reader, type)
    //    listaQuestoesAtual = listaQuestoesJSON.toMutableList()
    //    for (i in 0 until listaQuestoesAtual.size) {
    //        listaQuestoesAtual[i].condutasDisponiveis = mutableListOf(Conduta.BOA, Conduta.MA)
    //    }
    }
}

