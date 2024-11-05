package com.example.entregaapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import Personagem

class PersonagemAdapter(
    context: Context,
    private val personagens: List<Personagem>
) : ArrayAdapter<Personagem>(context, android.R.layout.simple_list_item_single_choice, personagens) {

     fun getView(position: Int, convertView: ViewGroup?, parent: ViewGroup): View {
        val personagem = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_single_choice, parent, false)

        // Atualiza a exibição do item
        view.findViewById<TextView>(android.R.id.text1).text = personagem?.toString() // Aqui chama o toString()

        return view
    }
}