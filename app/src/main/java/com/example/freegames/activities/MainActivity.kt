package com.example.freegames.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freegames.R
import com.example.freegames.adapters.GameAdapter
import com.example.freegames.data.Game
import com.example.freegames.data.GameService
import com.example.freegames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var binding: ActivityMainBinding
lateinit var adapter: GameAdapter
var gameList : List<Game> = emptyList()


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter = GameAdapter(gameList) { position ->
            val game = gameList[position.toInt()]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("GAME_ID", game.id)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        searchGames("a")

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchGames(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
     fun searchGames(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
            val service = GameService.getInstance()
            gameList = listOf(service.getGameById(query))
            CoroutineScope(Dispatchers.Main).launch {
                adapter.updateItems(gameList)
                Log.i("API", "$gameList")
            }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}