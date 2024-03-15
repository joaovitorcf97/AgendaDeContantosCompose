package com.example.agendadecontantoscompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.agendadecontantoscompose.constants.Constants

@Entity(tableName = Constants.TABELA_CONTATOS)
data class Contato(
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "sobrenome") val sobreNome: String,
    @ColumnInfo(name = "idade") val idade: String,
    @ColumnInfo(name = "telefone") val telefone: String,
    @PrimaryKey(autoGenerate = true)
    var uid: Int  = 0
)

