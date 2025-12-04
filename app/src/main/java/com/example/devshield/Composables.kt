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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Activity
import android.content.ClipData
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget

//imports do tutorial
/*import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
*/

import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import android.content.ClipDescription
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.Webhook
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Storm
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Security
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TelaBase(vm: MainViewModel) {
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

    // Ícones dos virus
    val icones = listOf(
        Icons.Default.BugReport,
        Icons.Default.Coronavirus,
        Icons.Default.Webhook,
        Icons.Default.Gesture,
        Icons.Default.Storm)

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
            if (vm.telaAtual == Tela.NOVO_JOGO) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Logo",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(36.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text =  "dev_shield",
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            else if (vm.telaAtual != Tela.ENCERRAMENTO) {
                Text(
                    text =  jogadorAtual.nome,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
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
                        for (i in 0 until NUM_MAX_VIRUS_POSICIONAVEIS) {
                            if (mapaAtual.mapaColunaVirus[i] != -1) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .dragAndDropSource(
                                            transferData = { _ ->
                                                DragAndDropTransferData(
                                                    ClipData.newPlainText("origemVirus","${i},-1")
                                                )
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = icones[i],
                                            contentDescription = "vírus $i",
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .size(36.dp)
                                        )
                                }
                            }
                            else {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) { }
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
                    if (vm.telaAtual in listOf(Tela.NOVO_MAPA, Tela.CONDUTA) || (vm.telaAtual == Tela.FEEDBACK && vm.jogadorAtual == 2)) {
                        var acao: () -> Unit = {}
                        if (vm.telaAtual == Tela.NOVO_MAPA) {
                            if (vm.jogadorAtual == 1) acao = { vm.telaAtual = Tela.NOVO_JOGO }
                            else acao = { vm.jogadorAtual = 1 }
                        }
                        else if (vm.telaAtual == Tela.CONDUTA) {
                            acao = { vm.telaAtual = Tela.SITUACAO }
                        }
                        else {
                            acao = { vm.jogadorAtual = 1 }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.clickable { acao() }
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
                    Column(modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth(),
                    ) {
                        // Endereços
                        for (i in 0 until NUM_MAX_LINHAS) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                for (j in 0 until NUM_MAX_COLUNAS) {
                                    lateinit var modifierBase: Modifier

                                    val enderecoAtual = mapaAtual.mapaEnderecos[i][j]

                                    val dragAndDropTarget =
                                        object : DragAndDropTarget {
                                            override fun onDrop(event: DragAndDropEvent): Boolean {
                                                val textoOrigem =
                                                    event.toAndroidDragEvent().clipData
                                                        ?.getItemAt(0)?.text?.toString()?.split(",")

                                                var linhaOrigem = -1
                                                var colunaOrigem = -1

                                                if (textoOrigem != null) {
                                                    linhaOrigem = textoOrigem[0].toInt()
                                                    colunaOrigem = textoOrigem[1].toInt()
                                                }

                                                // Vírus vem da área de vírus
                                                if (colunaOrigem == -1) {
                                                    mapaAtual.utilizaVirus(linhaOrigem)
                                                    enderecoAtual.posicionaVirus(
                                                        linhaOrigem
                                                    )
                                                }
                                                // Vírus vem de outra célula do mapa
                                                else {
                                                    val enderecoAntigo =
                                                        mapaAtual.mapaEnderecos[linhaOrigem][colunaOrigem]
                                                    enderecoAtual.posicionaVirus(
                                                        enderecoAntigo.idVirus
                                                    )
                                                    enderecoAntigo.perdeVirus()
                                                }
                                                return true
                                            }
                                        }

                                    // Configura endereço para receber vírus
                                    if (vm.telaAtual == Tela.NOVO_MAPA) {
                                        if (enderecoAtual.contemVirus() == false) {
                                            modifierBase = Modifier
                                                // Endereço serve como destino de vírus
                                                .dragAndDropTarget(
                                                    shouldStartDragAndDrop = { event ->
                                                        event
                                                            .mimeTypes()
                                                            .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                                    },
                                                    target = dragAndDropTarget
                                                )
                                                .aspectRatio(1f)
                                                .background(Color.White)
                                                .border(width = 2.dp, color = Color.Black)
                                        } else {
                                            // Endereço serve como origem de vírus
                                            modifierBase = Modifier
                                                .dragAndDropSource(
                                                    transferData = {
                                                        DragAndDropTransferData(
                                                            ClipData.newPlainText(
                                                                "origemVirus",
                                                                "$i,$j"
                                                            )
                                                        )
                                                    }
                                                )
                                                .aspectRatio(1f)
                                                .background(Color.White)
                                                .border(width = 2.dp, color = Color.Black)
                                        }
                                    }
                                    // Tela atual é INSPECAO
                                    else {
                                        if (enderecoAtual.estaVisivel) {
                                            modifierBase = Modifier
                                                .background(Color.White)
                                                .aspectRatio(1f)
                                                .border(width = 2.dp, color = Color.Black)
                                        } else {
                                            modifierBase = Modifier
                                                .background(Color.Gray)
                                                .aspectRatio(1f)
                                                .border(width = 2.dp, color = Color.Black)
                                                .clickable(enabled = vm.resultadoInspecaoAtual == ResultadoInspecao.PENDENTE || vm.resultadoInspecaoAtual == ResultadoInspecao.REPETE_JOGADA) {
                                                    mapaAtual.revelaEndereco(i,j)
                                                }
                                        }
                                    }

                                    Box(
                                        modifierBase,
                                         //   .weight(1f)
                                        //    .aspectRatio(1f)
                                        //    .border(width = 2.dp, color = Color.Black),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (enderecoAtual.contemVirus() && enderecoAtual.estaVisivel) {
                                            Icon(
                                                imageVector = icones[enderecoAtual.idVirus],
                                                contentDescription = "vírus",
                                                tint = Color.Black,
                                                modifier = Modifier
                                                    .size(36.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Texto grande acima e Botões abaixo
                else {
                    // Nomes para a tela NOVO_JOGO
                    var nomeJogador1 by remember { mutableStateOf("") }
                    var nomeJogador2 by remember { mutableStateOf("") }

                    // Texto grande
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (vm.telaAtual == Tela.NOVO_JOGO) {
                            // Nome do jogador 1
                            Row(modifier = Modifier
                                .fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(4f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    OutlinedTextField(
                                        value = nomeJogador1,
                                        onValueChange = { novoTexto ->
                                            if (novoTexto.length <= NUM_CARACTERES_NOME_JOGADOR) {
                                                nomeJogador1 = novoTexto
                                            }
                                        },
                                        label = { Text("Dev 1") },
                                        placeholder = { Text("Dev 1") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color.DarkGray,
                                            unfocusedBorderColor = Color.Gray,
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                            .fillMaxHeight(0.5f)
                                            .background(Color.Gray, RoundedCornerShape(8.dp)),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true
                                    )
                                }
                                // vs.
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "vs.",
                                        color = Color.Black,
                                        fontSize = 20.sp
                                    )
                                }
                                // Nome do jogador 2
                                Box(
                                    modifier = Modifier
                                        .weight(4f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    OutlinedTextField(
                                        value = nomeJogador2,
                                        onValueChange = { novoTexto ->
                                            if (novoTexto.length <= NUM_CARACTERES_NOME_JOGADOR) {
                                                nomeJogador2 = novoTexto
                                            }
                                        },
                                        label = { Text("Dev 2") },
                                        placeholder = { Text("Dev 2") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color.DarkGray,
                                            unfocusedBorderColor = Color.Gray,
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                            .fillMaxHeight(0.5f)
                                            .background(Color.Gray, RoundedCornerShape(8.dp)),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true
                                    )
                                }
                            }
                        }
                        else if (vm.telaAtual == Tela.SITUACAO) {
                            Text(
                                text = vm.questaoAtual.situacao,
                                color = Color.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        else if (vm.telaAtual == Tela.CONDUTA) {
                            Text(
                                text = vm.textoCondutaAtual,
                                color = Color.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        else if (vm.telaAtual == Tela.EFEITO_CONDUTA) {
                            if (vm.desempenhoJogadaAtual == DesempenhoJogada.ACERTOU) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Acerto",
                                        tint = Color.Green,
                                        modifier = Modifier
                                            .size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Você ganhou um backup!",
                                        color = Color.Black,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                            else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    // Atenção!
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Cancel,
                                            contentDescription = "Erro",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .size(36.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Atenção!",
                                            color = Color.Black,
                                            fontSize = 20.sp
                                        )
                                    }
                                    var textoDesempenho = ""
                                    if (vm.desempenhoJogadaAtual == DesempenhoJogada.ERROU_CONDUTA_E_BOA) {
                                        textoDesempenho =
                                            "A conduta descrita é, de fato, adequada e configura uma boa prática!"
                                    } else if (vm.desempenhoJogadaAtual == DesempenhoJogada.ERROU_CONDUTA_E_MA) {
                                        textoDesempenho = vm.questaoAtual.efeitoMaConduta
                                    }
                                    Box(
                                        modifier = Modifier
                                            .weight(3f)
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = textoDesempenho,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        else if (vm.telaAtual == Tela.ENCERRAMENTO) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                // Atenção!
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${jogadorAtual.nome}\nganhou!",
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(3f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Stars,
                                        contentDescription = "Ganhou",
                                        tint = Color.Yellow,
                                        modifier = Modifier
                                            .size(36.dp)
                                    )
                                }
                            }

                        }
                        else if (vm.telaAtual == Tela.FEEDBACK) {
                            val totalQuestoes = jogadorAtual.respostasErradas.size + jogadorAtual.pontuacao
                            var titulosQuestoesErradas = ""
                            for (questao in jogadorAtual.respostasErradas)
                                titulosQuestoesErradas += "${questao.titulo} "
                            val scrollState = rememberScrollState()

                            Text(
                                text =  "respostas certas: ${jogadorAtual.pontuacao}/${totalQuestoes}\n" +
                                        "estudar mais:\n" +
                                        titulosQuestoesErradas,
                                color = Color.Black,
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .fillMaxWidth(),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
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
                            var textoBotao = ""
                            var acao : () -> Unit = {}

                            if (vm.telaAtual == Tela.NOVO_JOGO) {
                                textoBotao = "começar"
                                acao = remember(nomeJogador1, nomeJogador2) {
                                    if (nomeJogador1.isNotEmpty() && nomeJogador2.isNotEmpty()) {
                                        {
                                            vm.jogador1.nome = nomeJogador1
                                            vm.jogador2.nome = nomeJogador2
                                            editaMapa(vm)
                                        }
                                    } else {
                                        {}
                                    }
                                }
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
                                    .clickable { acao() },
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
                ) { }
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
                            modifier = Modifier.clickable { acao() }
                        )
                    }
                    else if (vm.telaAtual == Tela.NOVO_MAPA && vm.jogadorAtual == 1 && vm.mapaJogador1.numVirusPosicionados == NUM_MAX_VIRUS_POSICIONAVEIS) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Continuar",
                            tint = Color.Black,
                            modifier = Modifier.clickable { vm.jogadorAtual = 2 }
                        )
                    }
                    else if (vm.telaAtual == Tela.NOVO_MAPA && vm.jogadorAtual == 2 && vm.mapaJogador2.numVirusPosicionados == NUM_MAX_VIRUS_POSICIONAVEIS) {
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