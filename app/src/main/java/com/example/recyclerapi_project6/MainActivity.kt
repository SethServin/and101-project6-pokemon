package com.example.recyclerapi_project6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter

    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        adapter = PokemonAdapter(this, pokemonList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        loadPokemon()
    }

    private fun loadPokemon() {
        val client = AsyncHttpClient()

        client.get(
            "https://pokeapi.co/api/v2/pokemon?limit=300",
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    val results =
                        json.jsonObject.getJSONArray("results")

                    for (i in 0 until results.length()) {
                        val pokemonObj =
                            results.getJSONObject(i)

                        val name =
                            pokemonObj.getString("name")

                        val url =
                            pokemonObj.getString("url")

                        loadDetails(name, url)
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    throwable?.printStackTrace()
                }
            }
        )
    }

    private fun loadDetails(
        name: String,
        url: String
    ) {
        val client = AsyncHttpClient()

        client.get(
            url,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    val jsonObject = json.jsonObject
                    val id = jsonObject.getInt("id")
                    val sprites = jsonObject.getJSONObject("sprites")
                    val imageUrl = sprites.getString("front_default")
                    val types = jsonObject.getJSONArray("types")
                    val type = if (types.length() > 0) {
                        types.getJSONObject(0).getJSONObject("type").getString("name")
                    } else {
                        "unknown"
                    }

                    val pokemon = Pokemon(name, imageUrl, id, type)
                    pokemonList.add(pokemon)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    throwable?.printStackTrace()
                }
            }
        )
    }
}
