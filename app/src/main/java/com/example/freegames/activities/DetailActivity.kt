package com.example.freegames.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freegames.R
import com.example.freegames.data.Game
import com.example.freegames.data.GameService
import com.example.freegames.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var game: Game
lateinit var bindingDetail: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingDetail = ActivityDetailBinding.inflate((layoutInflater))
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra("GAME_ID")!!

    }

    fun loadData(id: String) {
        supportActionBar?.title = game.title
    }

    fun searchGames(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = GameService.getInstance()
                gameList = listOf(service.getGameById(query))
                CoroutineScope(Dispatchers.Main).launch {
                    loadData(query)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
