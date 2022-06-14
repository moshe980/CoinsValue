package com.giniapps.coinsvalue.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.giniapps.coinsvalue.R
import com.giniapps.coinsvalue.databinding.ActivityMainBinding
import com.giniapps.coinsvalue.view.boundary.CoinBoundary
import com.giniapps.coinsvalue.viewmodel.Viewmodel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!
    private val viewmodel: Viewmodel = Viewmodel()
    private val data = ArrayList<CoinBoundary>()
    private val imageTag = "imageTag"
    private val spanCount = 3
    private val xStart = 1f
    private val xEnd = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = GridLayoutManager(this, spanCount)

        viewmodel.getCoins()


        viewmodel.coins.observe(this) { coins ->
            coins.forEach {
                data.add(
                    CoinBoundary(
                        it.name,
                        String.format("%.3f", it.value).toDouble(),
                        it.isGreaterTanDollar
                    )
                )
            }
            val adapter = MyAdapter(data)

            adapter.setOnItemClickListener { position ->
                flipAnimationStart(position)
                binding.recyclerview[position].findViewById<TextView>(R.id.textView).text =
                    getString(R.string.shekel, data[position].value.toString())
            }
            binding.recyclerview.adapter = adapter
        }


    }

    private fun flipAnimationStart(position: Int) {
        val anime1 =
            initAnimation(binding.recyclerview[position].findViewWithTag(imageTag), xStart, xEnd)
        val anime2 =
            initAnimation(binding.recyclerview[position].findViewWithTag(imageTag), xEnd, xStart)
        anime1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                anime2.start()


            }
        })
        anime1.start()
    }

    private fun initAnimation(currentCard: ImageView, xStart: Float, xEnd: Float): ObjectAnimator {
        val anime = ObjectAnimator.ofFloat(currentCard, "scaleX", xStart, xEnd)
        anime.interpolator = DecelerateInterpolator()
        return anime
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}