package muhammedshamshadp.hope.test

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import muhammedshamshadp.hope.test.databinding.ActivityMainBinding
import muhammedshamshadp.hope.test.utlis.NetworkUtils

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        init()
        handleNetworkChanges()
    }
    private fun init() {
       mainViewModel.dataLoading.observe(this,{
           if (it) {
               binding.pBar.visibility = View.VISIBLE
           } else {
               binding.pBar.visibility = View.GONE
           }
       })
    }

    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                binding.pBar.visibility = View.GONE
                binding.netView.visibility = View.VISIBLE
                binding.netStatus.text = getString(R.string.no_net)
                binding.netView.setBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorDangerDark
                    )
                )
            } else {
                binding.pBar.visibility = View.GONE
                binding.netStatus.text = getString(R.string.back_net)
                binding.netView.apply {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.colorSuccessDark
                        )
                    )
                    animate()
                        .alpha(1f)
                        .setStartDelay(500.toLong())
                        .setDuration(1000)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                binding.netView.visibility = View.GONE
                            }
                        })
                }

            }
        })
    }
}