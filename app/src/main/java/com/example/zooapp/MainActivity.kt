package com.example.zooapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zooapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var animalAdapter : AnimalsAdapter

    var context = this
    var connectivity : ConnectivityManager? = null
    var info : NetworkInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val buttonClick = findViewById<Button>(R.id.button_click)
        buttonClick.setOnClickListener {
            connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE)
            as ConnectivityManager

            if (connectivity != null){
                info = connectivity!!.activeNetworkInfo

                if (info != null){
                    if(info!!.state == NetworkInfo.State.CONNECTED){
                        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()

                        animalAdapter = AnimalsAdapter(this)
                        binding.rvAnimals.layoutManager = LinearLayoutManager(this)
                        binding.rvAnimals.adapter = animalAdapter

                        lifecycleScope.launchWhenCreated {
                            binding.progressBar.isInvisible = true //wysylamy zapytanie i dopoki ono sie wykonuje progresbar ma byc widoczny
                            val response = try {
                                RetrofitInstance.api.getAnimals()
                            } catch (e: IOException) {
                                Log.e(TAG, "IOException, możliwe że nie masz połączenia z internetem")
                                binding.progressBar.isVisible = false
                                return@launchWhenCreated
                            } catch (e: HttpException) {
                                Log.e(TAG, "HttpException, nieoczekiwana odpowiedz")
                                binding.progressBar.isVisible = false
                                return@launchWhenCreated
                            }
                            if (response.isSuccessful && response.body() != null){ //sprawdzam czy odpowiedz serwera ma status OK i czy została zwrócona lista zwierząt
                                animalAdapter.animals = response.body()!! //ustawienie listy zwierzat do adaptera
                            } else {
                                Log.e(TAG, "Odpowiedź serwera nie powiodła się")
                            }
                            binding.progressBar.isVisible = false

                        }
                    }
                }else{
                    Toast.makeText(this, "NOT Connected", Toast.LENGTH_SHORT).show()

                    try {

                        val jsonString = getJSONFromAssets()
                        val animals = Gson().fromJson(jsonString, AnimalArray::class.java)

                        binding.rvAnimals.layoutManager = LinearLayoutManager(this)

                        val itemAdapter = AnimalAdapterAssets(this, animals.animals)

                        binding.rvAnimals.adapter = itemAdapter
                    } catch (e: JSONException) {

                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getJSONFromAssets(): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myAnimalsJSONFile = assets.open("zoo_api.json")
            val size = myAnimalsJSONFile.available()
            val buffer = ByteArray(size)
            myAnimalsJSONFile.read(buffer)
            myAnimalsJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}