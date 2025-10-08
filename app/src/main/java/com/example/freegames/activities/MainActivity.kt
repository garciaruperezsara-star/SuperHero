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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freegames.R
import com.example.freegames.adapters.GameAdapter
import com.example.freegames.data.Game
import com.example.freegames.data.GameService
import com.example.freegames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: GameAdapter
    var filteredGameList: List<Game> = emptyList()
    var originalGameList: List<Game> = emptyList()
    var gameList : List<Game> = emptyList()

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
        adapter = GameAdapter(gameList) { gameId ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("GAME_ID", gameId)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        getGameList()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filteredGameList = originalGameList.filter { it.title.contains(newText, true) }
                adapter.updateItems(filteredGameList)
                return true
            }
        })
        return true
    }
    fun getGameList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = GameService.getInstance()
                originalGameList = service.getAllGames()
                filteredGameList = originalGameList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredGameList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}