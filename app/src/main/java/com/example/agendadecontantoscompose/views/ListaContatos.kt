package com.example.agendadecontantoscompose.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendadecontantoscompose.AppDataBase
import com.example.agendadecontantoscompose.componentes.ContatoCard
import com.example.agendadecontantoscompose.dao.ContatoDao
import com.example.agendadecontantoscompose.model.Contato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var contantoDao: ContatoDao

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaContatos(navController: NavController) {
    val context = LocalContext.current
    var listaContatos: MutableList<Contato> = mutableListOf()
    val scope = rememberCoroutineScope()

    scope.launch(Dispatchers.IO) {
        contantoDao = AppDataBase.getInstance(context).contatoDao()
        listaContatos = contantoDao.buscarContantos()
    }

    fun alertDialogDeletarContato(uid: Int) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Deseja deletar")
            .setMessage("Tem certezar")

        alertDialog.setPositiveButton("Ok") { _, _ ->
            scope.launch(Dispatchers.IO) {
                contantoDao = AppDataBase.getInstance(context).contatoDao()
                contantoDao.deltarUsuario(uid)
            }

            scope.launch(Dispatchers.Main) {
                navController.navigate("listaContatos")
                Toast.makeText(context, "Contanto removido", Toast.LENGTH_LONG)
                    .show()
            }
        }

        alertDialog.setNegativeButton("Cancelar") { _, _ ->

        }

        alertDialog.show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Agenda de contatos")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("salvarContato")
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            itemsIndexed(listaContatos) { index, contato ->
                ContatoCard(
                    contato = contato,
                    navController,
                    onDelete = { alertDialogDeletarContato(contato.uid) },
                )
            }
        }
    }
}