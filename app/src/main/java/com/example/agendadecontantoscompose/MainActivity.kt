package com.example.agendadecontantoscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agendadecontantoscompose.ui.theme.AgendaDeContantosComposeTheme
import com.example.agendadecontantoscompose.views.AtualizarContanto
import com.example.agendadecontantoscompose.views.ListaContatos
import com.example.agendadecontantoscompose.views.SalvarContato

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaDeContantosComposeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "listaContatos"
                ) {
                    composable("listaContatos") {
                        ListaContatos(navController)
                    }

                    composable("salvarContato") {
                        SalvarContato(navController)
                    }

                    composable(
                        "atualizarContato/{uid}",
                        arguments = listOf(navArgument("uid") {})
                    ) {
                        AtualizarContanto(navController, it.arguments?.getString("uid").toString())
                    }
                }
            }
        }
    }
}
