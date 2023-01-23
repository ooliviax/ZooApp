package com.example.zooapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalAdapterAssets (val context: Context, val items: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalAdapterAssets.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_animal,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)

        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description.text

        Picasso.get().load(item.img).into(holder.animalImageView)

        holder.animalImageView.setOnClickListener{
            var animalIntent = Intent(context, AnimalAnimal::class.java)
            animalIntent.putExtra("animalName", item.name)
            animalIntent.putExtra("animalDesc", item.description.text)
            animalIntent.putExtra("animalImg", item.img)
            animalIntent.putExtra("animalType", item.description.type)
            animalIntent.putExtra("animalLife", item.details.lifespan)
            animalIntent.putExtra("animalWeightFemale", item.details.femaleWeight)
            animalIntent.putExtra("animalWeightMale", item.details.maleWeight)
            animalIntent.putExtra("animalPregnancy", item.details.periodOfPregnancy)
            context.startActivity(animalIntent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(val view: View, var ttTeam: AnimalArray?=null) : RecyclerView.ViewHolder(view) {
        val animalImageView = view.animal_imageView
        val nameTextView = view.name_textView
        val descriptionTextView = view.description_textView

    }
}