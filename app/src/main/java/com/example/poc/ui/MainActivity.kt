package com.example.poc.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.poc.R
import com.example.poc.model.ApiInterface
import com.example.poc.R.*
import com.example.poc.R.drawable.*
import com.example.poc.databinding.ActivityMainBinding
import com.example.poc.model.DataModelItem
import com.example.poc.notification.CounterNotificationService
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


    lateinit var retrofit: Retrofit
    lateinit var notify:CounterNotificationService
    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var job:Job?=null

        binding.switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                job = CoroutineScope(Dispatchers.IO).launch{
                    while (true) {
                        if (isActive){
                            getData()
                            delay(100)
                        }
                    }
                }
                binding.textView.text = "Syncing..."

            } else {
                job?.cancel()

                binding.tv.text = "Not Syncing"
                binding.log.text = "NaN"
            }
        }


        retrofit = Retrofit.Builder().baseUrl("https://io.adafruit.com/api/v2/pradeep03/feeds/")
            .addConverterFactory(GsonConverterFactory.create()).build()


        notify=CounterNotificationService(this)


    }

    @SuppressLint("SuspiciousIndentation")
    fun getData(){

        val api: ApiInterface = retrofit.create(ApiInterface::class.java)


        val call: Call<DataModelItem> = api.getData()
            call.enqueue(object : Callback<DataModelItem> {

                override fun onResponse(
                    call: Call<DataModelItem>,
                    response: Response<DataModelItem>
                ) {
                    if (response.isSuccessful) {
                        Log.d("TAG", "onResponse: ${response.body()}")
                        binding.tv.text=response.body()?.last_value
                        binding.log.text=response.body()?.updated_at
                        if (response.body()?.last_value=="1"){
                            setImageAlert()
                            notify.showNotification()
                        }else{
                            unsetImageAlert()
                        }
                    }

                }
                override fun onFailure(call: Call<DataModelItem>, t: Throwable) {
                    Log.d("TAG", "onResponse: $t")
                }
            })
        }


    fun setImageAlert(){
        binding.image.visibility= View.VISIBLE
    }
    fun unsetImageAlert(){
        binding.image.visibility= View.GONE
    }



}






