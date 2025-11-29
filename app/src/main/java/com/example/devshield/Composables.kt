package com.example.devshield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview(showBackground = true)
@Composable
fun TelaBase(vm: MainViewModel = MainViewModel()) {
    // Texto de teste
    val txt = "X"

    // Identifica objetos correspondentes ao jogador e ao mapa atuais
    var jogadorAtual: Jogador
    var adversario: Jogador
    var mapaAtual: Mapa
    if (vm.jogadorAtual == 1) {
        jogadorAtual = vm.jogador1
        adversario = vm.jogador2
        mapaAtual = vm.mapaJogador1
    }
    else {
        jogadorAtual = vm.jogador2
        adversario = vm.jogador1
        mapaAtual = vm.mapaJogador2
    }

    // Variável necessária para encerra aplicação
    val activity = LocalContext.current as? Activity

    // Tela base
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.safeDrawing.asPaddingValues()) // respeita status bar e barra de navegação
    ) {
        // Menu superior (Row)
        Row(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
        ) {
            // Nome da aplicação (Box)
            Box(modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (vm.telaAtual != Tela.NOVO_JOGO) {
                    Text(
                        text = "dev_shield",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
            // Backups (Box)
            Box(modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (vm.telaAtual in listOf(Tela.SITUACAO, Tela.CONDUTA, Tela.EFEITO_CONDUTA, Tela.INSPECAO)) {
                    Text(
                        text = "${mapaAtual.numBackupsRestantes} bkps",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
            // Sair (Box)
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Sair",
                    tint = Color.Black,
                    modifier = Modifier.clickable { activity?.finish() }
                )
            }
        }

        // Texto superior (com ou sem ícone) (Box)
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (vm.telaAtual != Tela.ENCERRAMENTO) {
                Text ( text = txt )
            }
        }
        // Conteúdo principal (Row)
        Row(modifier = Modifier
            .weight(4f)
            .fillMaxWidth()
        ) {
            // Lateral esquerda (Column)
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ) {
                // Ícones de vírus (Column)
                Column(modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                ) {
                    if (vm.telaAtual == Tela.NOVO_MAPA) {
                        // Virus individuais
                        for (i in 0 until 5) {
                            Box(modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text ( text = txt )
                            }
                        }
                    }
                }
                // Botão de retorno (Box)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (vm.telaAtual in listOf(Tela.NOVO_MAPA, Tela.CONDUTA, Tela.FEEDBACK)) {
                        var acao: () -> Unit = {}
                        if (vm.telaAtual == Tela.NOVO_MAPA) {
                            if (vm.jogadorAtual == 1) acao = { vm.telaAtual = Tela.NOVO_JOGO }
                            else acao = { vm.jogadorAtual = 1 }
                        }
                        else if (vm.telaAtual == Tela.CONDUTA) {
                            vm.telaAtual = Tela.SITUACAO
                        }
                        else {
                            vm.jogadorAtual = 1
                        }

                            vm.telaAtual = Tela.SITUACAO
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.clickable { acao }
                        )
                    }
                }
            }

            // Centro (Box)
            Column(modifier = Modifier
                .weight(6f)
                .fillMaxWidth(),
            ) {
                // Texto curto acima e Mapa abaixo
                if (vm.telaAtual in listOf(Tela.NOVO_MAPA, Tela.INSPECAO)) {
                    // Texto curto
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        var texto = ""
                        var icone = 0
                        if (vm.telaAtual == Tela.NOVO_MAPA) {
                            texto = "posicione 5 vírus na SSD do seu oponente:"
                        }
                        else {
                            if (vm.resultadoInspecaoAtual == ResultadoInspecao.PENDENTE) {
                                texto = "inspecione um endereço da sua SSD:"
                            }
                            else if (vm.resultadoInspecaoAtual == ResultadoInspecao.VIRUS) {
                                texto = "endereço corrompido: você perdeu 1 backup!"
                                icone = 1
                            }
                            else if (vm.resultadoInspecaoAtual == ResultadoInspecao.NAO_VIRUS) {
                                texto = "endereço íntegro: boa escolha!:"
                                icone = 2
                            }
                            else if (vm.resultadoInspecaoAtual == ResultadoInspecao.REPETE_JOGADA) {
                                texto = "bloco íntegro: escolha mais um endereço!"
                                icone = 2
                            }
                            else if (vm.resultadoInspecaoAtual == ResultadoInspecao.ACABOU_MEMORIA) {
                                texto = "endereço corrompido e backup inexistente!"
                                icone = 1
                            }
                            else if (vm.resultadoInspecaoAtual == ResultadoInspecao.REVELOU_TUDO) {
                                texto = "inspeção concluída: todos os vírus detectados!"
                                icone = 2
                            }
                        }
                        if (icone == 0) {
                            Text(
                                text = texto,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                        else if (icone == 1) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Ruim",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = texto,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                        else {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Bom",
                                tint = Color.Green,
                                modifier = Modifier
                                    .size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = texto,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                    // Mapa
                    Box(modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                }
                // Texto grande acima e Botões abaixo
                else {
                    // Texto grande
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (vm.telaAtual == Tela.NOVO_JOGO) {

                        }
                        else if (vm.telaAtual == Tela.SITUACAO) {

                        }
                        else if (vm.telaAtual == Tela.CONDUTA) {

                        }
                        else if (vm.telaAtual == Tela.EFEITO_CONDUTA) {

                        }
                        else if (vm.telaAtual == Tela.ENCERRAMENTO) {

                        }
                        else if (vm.telaAtual == Tela.FEEDBACK) {

                        }
                    }
                    // Botões
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        // DOIS BOTÕES
                        if (vm.telaAtual == Tela.CONDUTA) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.Center) // centraliza na Box
                                    .padding(16.dp),         // padding interno
                                horizontalArrangement = Arrangement.spacedBy(24.dp), // distância entre ícones
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = "Aprovar",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable { exibeEfeitoConduta(vm, Conduta.BOA) }
                                )
                                Icon(
                                    imageVector = Icons.Default.ThumbDown,
                                    contentDescription = "Rejeitar",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clickable { exibeEfeitoConduta(vm, Conduta.MA)  }
                                )
                            }
                        }
                        // UM BOTÃO
                        else {
                            // Identifica texto do botão e ação correspondente
                            var textoBotao : String = ""
                            var acao : () -> Unit = {}

                            if (vm.telaAtual == Tela.NOVO_JOGO) {
                                textoBotao = "começar"
                                // PENDENCIA: PRECISA RECEBER OS VALORES DOS NOMES DOS JOGADORES!!!
                                acao = { editaMapa(vm) }
                            }
                            else if (vm.telaAtual == Tela.SITUACAO) {
                                textoBotao = "avaliar conduta"
                                acao = { exibeConduta(vm) }
                            }
                            else if (vm.telaAtual == Tela.EFEITO_CONDUTA) {
                                textoBotao = "inspecionar SSD"
                                acao = { exibeMapa(vm) }
                            }
                            else if (vm.telaAtual == Tela.ENCERRAMENTO) {
                                textoBotao = "receber feedback"
                                acao = { exibeFeedback(vm, 1) }
                            }
                            else if (vm.telaAtual == Tela.FEEDBACK) {
                                if (vm.jogadorAtual == 1) {
                                    textoBotao = "continuar feedback"
                                    acao = { exibeFeedback(vm, 2) }
                                }
                                else {
                                    textoBotao = "jogar novamente"
                                    acao = { reiniciaJogo(vm) }
                                }
                            }
                            // Cria botão
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1/3f)     // 1/3 da largura do Box pai
                                    .fillMaxHeight(1/2f)    // 1/2 da altura do Box pai
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.Black)
                                    .clickable { acao },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = textoBotao,
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            // Lateral direita (Column)
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ) {
                // Espaço em branco (Box)
                Box(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = txt)
                }
                // Botão de avanço (Box)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (vm.telaAtual == Tela.INSPECAO && vm.resultadoInspecaoAtual != ResultadoInspecao.PENDENTE && vm.resultadoInspecaoAtual != ResultadoInspecao.REPETE_JOGADA) {
                        var acao: () -> Unit = {}
                        // Não era vírus
                        if (vm.resultadoInspecaoAtual == ResultadoInspecao.NAO_VIRUS)
                            acao = {
                                vm.jogadorAtual = adversario.id
                                iniciaNovaRodada(vm)
                            }
                        // Era vírus
                        else if (vm.resultadoInspecaoAtual == ResultadoInspecao.VIRUS)
                            acao = {
                                vm.jogadorAtual = adversario.id
                                iniciaNovaRodada(vm)
                            }
                        // Jogador perdeu
                        else if (vm.resultadoInspecaoAtual == ResultadoInspecao.ACABOU_MEMORIA)
                            acao = { encerraJogo(vm, adversario.id) }
                        // Jogador venceu
                        else if (vm.resultadoInspecaoAtual == ResultadoInspecao.REVELOU_TUDO)
                            acao = { encerraJogo(vm, jogadorAtual.id) }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Continuar",
                            tint = Color.Black,
                            modifier = Modifier.clickable { acao }
                        )
                    }
                    else if (vm.telaAtual == Tela.NOVO_MAPA && vm.jogadorAtual == 1) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Continuar",
                            tint = Color.Black,
                            modifier = Modifier.clickable { vm.jogadorAtual = 2 }
                        )
                    }
                    else if (vm.telaAtual == Tela.NOVO_MAPA && vm.jogadorAtual == 2) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Continuar",
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                vm.jogadorAtual = 1
                                iniciaNovaRodada(vm)
                            }
                        )
                    }
                }
            }
        }
    }
}