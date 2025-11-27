package com.example.devshield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devshield.ui.theme.DevShieldTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DevShieldTheme {
                TelaBase( )
            }
        }
    }
}

// Classe que declara e manipula variáveis
class MainViewModel : ViewModel() {
    var texto1 by mutableStateOf("Olá")
    var texto2 by mutableStateOf("Minha Tela")
}

// Tela base
@Preview(showBackground = true)
@Composable
fun TelaBase(vm: MainViewModel = MainViewModel()) {
    // Instancia view model, em que variáveis serão declaradas e manipuladas
    val vm: MainViewModel = viewModel()

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
                Text ( text = txt )
            }
            // Pontuação (Box)
            Box(modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text ( text = txt )
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
            Text ( text = txt )
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
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text ( text = txt )
                    }
                }
                // Botão de retorno (Box)
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text ( text = txt )
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
                // Espaço em branco (Box)
                Box(modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text ( text = txt )
                }
                // Botão de avanço (Box)
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
}

