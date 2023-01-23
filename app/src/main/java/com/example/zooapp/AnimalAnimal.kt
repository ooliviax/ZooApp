package com.example.zooapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class AnimalAnimal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animal_animal)

        val textView: TextView = findViewById(R.id.tv1)
        val textView2: TextView = findViewById(R.id.tv2)
        val Type: TextView = findViewById(R.id.tvType)
        val Lifespan: TextView = findViewById(R.id.tvLife)
        val FemaleWeight: TextView = findViewById(R.id.tvFemale)
        val MaleWeight: TextView = findViewById(R.id.tvMale)
        val Pregnancy: TextView = findViewById(R.id.tvPeriod)
        val imageView: ImageView = findViewById(R.id.imgV)

        val animalData = intent
        val animalName = animalData.getStringExtra("animalName")
        val animalDesc = animalData.getStringExtra("animalDesc")
        val animalImg = animalData.getStringExtra("animalImg")
        val animalType = animalData.getStringExtra("animalType")
        val animalLife = animalData.getStringExtra("animalLife")
        val animalWeightFemale = animalData.getStringExtra("animalWeightFemale")
        val animalWeightMale = animalData.getStringExtra("animalWeightMale")
        val animalPregnancy = animalData.getStringExtra("animalPregnancy")

        textView.text = animalName
        textView2.text = animalDesc
        Type.text = animalType
        Lifespan.text = animalLife
        FemaleWeight.text = animalWeightFemale
        MaleWeight.text = animalWeightMale
        Pregnancy.text = animalPregnancy
        Picasso.get().load(animalImg).into(imageView)
    }
}