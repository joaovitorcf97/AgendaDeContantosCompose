package com.example.agendadecontantoscompose.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.agendadecontantoscompose.model.Contato

@Dao
interface ContatoDao {
    @Insert
    fun criar(contato: Contato)

    @Query("SELECT * FROM tabela_contatos ORDER BY nome ASC")
    fun buscarContantos(): MutableList<Contato>

    @Query("UPDATE tabela_contatos SET nome = :novoNome, sobreNome = :novoSobreNome, idade = :novaIdade, telefone = :novoTelefone WHERE uid = :id")
    fun atualizarContanto(id: Int, novoNome: String, novoSobreNome: String, novaIdade: String, novoTelefone: String)

    @Query("DELETE FROM tabela_contatos WHERE uid = :id")
    fun deltarUsuario(id: Int)
}