package bvr.blockgame.game.blockreact

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_loose.*
import android.widget.Toast
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdListener




class Loose : AppCompatActivity() {

    private lateinit var sharedPreference:SharedPreference
    val SAVED_TEXT = "score"
    private lateinit var mInterstitialAd: InterstitialAd
   companion object {
        const val TOTAL_SCORE="total_score"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_loose)
        sharedPreference=SharedPreference(this)
        getScore()

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-7026303595758317/8602332068"
        val adRequestBuilder = AdRequest.Builder()

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {

                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
            }

            override fun onAdClosed() {
                // Proceed to the next level.

            }
        }
        mInterstitialAd.loadAd(adRequestBuilder.build())


    }
    fun getScore (){
        val score= intent.getIntExtra(TOTAL_SCORE,0)
        scoredigit.text=score.toString()
        if(sharedPreference.getValueScoreMoney(SAVED_TEXT)<score){
            sharedPreference.save(SAVED_TEXT,score)
        }
        bestscoredigit.text=sharedPreference.getValueScoreMoney(SAVED_TEXT).toString()
    }
    fun onClickMenu (view: View){
        val button = findViewById<View>(R.id.mainmenu) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        val gameAct = Intent (this, Main::class.java)
        finish()
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(gameAct)
    }
    fun onClickRestart (view: View){
        val button = findViewById<View>(R.id.restart) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        val gameAct = Intent (this, Game::class.java)
        finish()
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(gameAct)
    }


}
