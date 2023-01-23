package com.example.zooapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zooapp.databinding.ItemAnimalBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class AnimalsAdapter(private val c: Context) : RecyclerView.Adapter<AnimalsAdapter.AnimalsViewHolder>() {

    lateinit var mainActivity : AppCompatActivity

    inner class AnimalsViewHolder(val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Animal>(){
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var animals : List<Animal>
        get() = differ.currentList
        set(value) { differ.submitList(value) }


    override fun getItemCount() = animals.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        return AnimalsViewHolder(ItemAnimalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        holder.binding.apply {
            val animal = animals[position]
            nameTextView.text = animal.name
            descriptionTextView.text = animal.description.text
            Picasso.get().load(animal.img).into(animalImageView)

            animalImageView.setOnClickListener{
                    var animalIntent = Intent(c, AnimalAnimal::class.java)
                    animalIntent.putExtra("animalName", animal.name)
                    animalIntent.putExtra("animalDesc", animal.description.text)
                    animalIntent.putExtra("animalImg", animal.img)
                    animalIntent.putExtra("animalType", animal.description.type)
                    animalIntent.putExtra("animalLife", animal.details.lifespan)
                    animalIntent.putExtra("animalWeightFemale", animal.details.femaleWeight)
                    animalIntent.putExtra("animalWeightMale", animal.details.maleWeight)
                    animalIntent.putExtra("animalPregnancy", animal.details.periodOfPregnancy)
                    c.startActivity(animalIntent)
            }
        }
    }

}