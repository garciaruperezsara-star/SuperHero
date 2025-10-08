package com.example.freegames.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freegames.R
import com.example.freegames.data.Game
import com.example.freegames.data.GameService
import com.example.freegames.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var game: Game
    lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("GAME_ID")!!

        getGame(id)
    }

    fun loadData() {
        binding.gameDescription.text = game.description
        binding.gameName.text = game.title
        Picasso.get().load(game.thumbnail).into(binding.gameDetailImageView)
//        binding.playButton.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, game.gameUrl.toUri())
//            startActivity(browserIntent)
//        }
    }

    fun getGame(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = GameService.getInstance()
                game = service.getGameById(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
