package com.example.devshield

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview(showBackground = true)
@Composable
fun TelaBase(vm: MainViewModel = MainViewModel()) {
    // Texto de teste
    val txt = "X"

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
                    Text(text = txt)
                }
            }
            // Backups (Box)
            Box(modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (vm.telaAtual in listOf(Tela.SITUACAO, Tela.CONDUTA, Tela.EFEITO_CONDUTA, Tela.INSPECAO)) {
                    Text(text = txt)
                }
            }
            // Sair (Box)
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text ( text = txt )
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
                if (vm.telaAtual in listOf(Tela.NOVO_MAPA, Tela.CONDUTA, Tela.FEEDBACK)) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = txt)
                    }
                }
            }

            // Centro (Box)
            Box(modifier = Modifier
                .weight(6f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text ( text = txt )
            }
            // 2 alternativas:
            // Texto curto + campo minado (Column com duas Boxes)
            // Texto longo + botões (Column com duas Boxes)
            // Lateral direita (Column)
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ) {
                if (vm.telaAtual == Tela.INSPECAO && vm.jogadaTerminou) {
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
                        Text(text = txt)
                        // CONTEÚDO DO BOTÃO CONFORME O CASO
                        //                 exibeMapa(vm)
                        //                iniciaNovaRodada(vm)
                    }
                }
            }
        }
    }
}