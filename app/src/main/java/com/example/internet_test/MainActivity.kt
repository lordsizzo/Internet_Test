package com.example.internet_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.internet_test.adapters.Books_Adapter
import com.example.internet_test.databinding.ActivityMainBinding
import com.example.internet_test.model.BookItem
import com.example.internet_test.model.BooksResponse
import com.example.internet_test.model.layoutManagerCustom
import com.example.internet_test.model.remote.BooksApi
import com.example.internet_test.model.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var increase: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*Thread(Runnable {
            Network().executeNetworkCall("pride prejudice", 5, "books").let {
                //items = it

                items = it.items

            }
        }).start()*/

        initRetrofit()
        binding.swipeRefreshLayout.setOnRefreshListener {
            increase += 1
            initRetrofit()
        }
    }

    fun initRetrofit() {
        BooksApi.initRetrofit().findBooksByTitle("pride prejudice", increase, "books").enqueue(
            object : Callback<BooksResponse> {
                override fun onResponse(
                    call: Call<BooksResponse>,
                    response: Response<BooksResponse>
                ) {
                    if(response.isSuccessful) {
                        response.body()?.let {
                            updateUI(it)
                        } ?: errorMessage(response.message())
                    }
                }

                override fun onFailure(call: Call<BooksResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
    val TAG = "MainActivity"
    private fun errorMessage(message: String) {
        Log.d(TAG, "errorMessage: $message")
        toast(message)
    }

    private fun updateUI(it: BooksResponse) {
        binding.recyclerView.layoutManagerCustom()
        binding.recyclerView.adapter = Books_Adapter(it.items, this)
        binding.swipeRefreshLayout.isRefreshing = false
        Log.d(TAG, "updateUI: ${it.items.size}")
    }
}