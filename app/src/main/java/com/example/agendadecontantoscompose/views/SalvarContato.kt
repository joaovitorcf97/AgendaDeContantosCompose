package com.example.agendadecontantoscompose.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendadecontantoscompose.AppDataBase
import com.example.agendadecontantoscompose.componentes.Botao
import com.example.agendadecontantoscompose.componentes.TextFieldCustom
import com.example.agendadecontantoscompose.dao.ContatoDao
import com.example.agendadecontantoscompose.model.Contato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var contatoDao: ContatoDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvarContato(navController: NavController) {
    val listContatos: MutableList<Contato> = mutableListOf()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var nome by remember {
        mutableStateOf("")
    }

    var sobreNome by remember {
        mutableStateOf("")
    }

    var idade by remember {
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Salvar contato")
                },
            )
        },
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldCustom(
                value = nome,
                label = "Nome",
                onValueChange = { nome = it },
                keyboardType = KeyboardType.Text,
            )

            TextFieldCustom(
                value = sobreNome,
                label = "Sobrenome",
                onValueChange = { sobreNome = it },
                keyboardType = KeyboardType.Text,
            )

            TextFieldCustom(
                value = idade,
                label = "Idade",
                onValueChange = { idade = it },
                keyboardType = KeyboardType.Number,
            )

            TextFieldCustom(
                value = telefone,
                label = "Telefone",
                onValueChange = { telefone = it },
                keyboardType = KeyboardType.Number,
            )

            Botao(
                title = "Salvar",
                onClick = {
                    var mensagem = false
                    scope.launch(Dispatchers.IO) {
                        if (nome.isEmpty() || sobreNome.isEmpty() || idade.isEmpty() || telefone.isEmpty()) {
                            mensagem = false
                        } else {
                            mensagem = true
                            val contato = Contato(nome, sobreNome, idade, telefone)

                            contatoDao = AppDataBase.getInstance(context).contatoDao()
                            contatoDao.criar(contato)
                        }
                    }

                    scope.launch(Dispatchers.Main) {
                        if (mensagem) {
                            Toast.makeText(
                                context,
                                "Sucesso ao salvar o contanto",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            )
        }
    }
}