package bvr.blockgame.game.blockreact



import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.games.Games
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer
import android.view.View.OnTouchListener
import android.media.AudioManager
import android.widget.SeekBar
import android.widget.Toast
import bvr.blockgame.game.blockreact.R.id.bestScore
import kotlin.math.sign
import android.text.method.LinkMovementMethod
import android.widget.TextView




class Main : AppCompatActivity() {

    var n=1
    val SAVED_TEXT = "score"
    val SAVED_COIN = "coin"
    val SAVED_SOUND = "sound"
    val SAVED_MUSIC = "music"
    val SAVED_PLAY = "play"
    lateinit var mp:MediaPlayer
    lateinit var mpTap:MediaPlayer
    private lateinit var sharedPreference:SharedPreference
    private var apiClient: GoogleApiClient? = null
    private var score=0

    private lateinit var manager:AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        mpTap = MediaPlayer.create(this, R.raw.click1)
        mp = MediaPlayer.create(this, R.raw.estimable)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)



        setContentView(R.layout.activity_main)
        val t2 = findViewById<View>(R.id.text2) as TextView
        t2.movementMethod = LinkMovementMethod.getInstance()
        sharedPreference=SharedPreference(this@Main)
        //start google auth

        apiClient = GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(bundle: Bundle?) {
                        if (apiClient != null) {


                        }


                    }

                    override fun onConnectionSuspended(i: Int) {


                    }
                })
                .enableAutoManage(this) {
                    Log.e("disconnect", "Could not connect to Play games services")

                }.build()

//end google auth


        best.text=sharedPreference.getValueScoreMoney(SAVED_TEXT).toString()
        moneyShop.text=sharedPreference.getValueScoreMoney(SAVED_COIN).toString()
        if(sharedPreference.getValueInt(SAVED_PLAY)==0){

        }else{
            signtext.text="Sign Out"
        }
        //music
            mp.setLooping(true)

            manager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                if (!manager.isMusicActive) {
                    mp.start()
                 }

            mp.setVolume(sharedPreference.getValueInt(SAVED_MUSIC).toFloat()/100,
                    sharedPreference.getValueInt(SAVED_MUSIC).toFloat()/100)
            mpTap.setVolume(sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100,
                    sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100)
            sound.progress=sharedPreference.getValueInt(SAVED_SOUND)
            music.progress=sharedPreference.getValueInt(SAVED_MUSIC)
        //end music
        main.setOnClickListener {
            setting.visibility=View.GONE
            play.visibility=View.VISIBLE
        }



        sound.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(this@Main, "Sound is " + seekBar.progress + "%", Toast.LENGTH_SHORT).show()
                sharedPreference.save(SAVED_SOUND,seekBar.progress)
                mpTap.setVolume(sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100,
                        sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100)

            }

        })
        music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(this@Main, "Music is " + seekBar.progress + "%", Toast.LENGTH_SHORT).show()
               /* var volume:Float=seekBar.progress.toFloat()
                volume /= 100*/
                sharedPreference.save(SAVED_MUSIC,seekBar.progress)
                mp.setVolume(sharedPreference.getValueInt(SAVED_MUSIC).toFloat()/100,
                        sharedPreference.getValueInt(SAVED_MUSIC).toFloat()/100)
            }

        })
        val ha = Handler()

        ha.postDelayed(object : Runnable {

            override fun run() {

              //  buttonAnima()
                imageAnimation()
                ha.postDelayed(this, 2000)
            }
        }, 2000)


    }

    override fun onPause() {
        super.onPause()
       mp.pause()

    }

    override fun onResume() {
        super.onResume()
        mp.start()
    }

    fun didTapButton(view: View) {
        mpTap.start()
        val button = findViewById<View>(R.id.play) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        val gameAct = Intent (this, Game::class.java)
        finish()
        startActivity(gameAct)

    }
    fun didTapRank(view: View) {
        if(!apiClient!!.isConnected){
            Toast.makeText(this@Main, "You must to connect Play Games", Toast.LENGTH_LONG).show()
            mpTap.start()
            val button = findViewById<View>(R.id.trophy) as ImageButton
            val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            button.startAnimation(myAnim)

        }
        if(apiClient!!.isConnected) {
            mpTap.start()
            val button = findViewById<View>(R.id.trophy) as ImageButton
            val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            button.startAnimation(myAnim)
            Games.Leaderboards.submitScore(apiClient,
                    getString(R.string.leaderboard_blockreact_leaderboard), sharedPreference.getValueInt(SAVED_TEXT).toLong()
            )
            startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(apiClient,
                            getString(R.string.leaderboard_blockreact_leaderboard)), 0)
        }
    }
    fun didTapShop(view: View) {
        mpTap.start()
        val button = findViewById<View>(R.id.mart) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        val gameAct = Intent (this, Shop::class.java)
        finish()
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(gameAct)
    }
    fun didTapSettings(view: View) {
        mpTap.start()
        val button = findViewById<View>(R.id.settings) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        play.visibility=View.INVISIBLE
        setting.visibility=View.VISIBLE
        if(!apiClient!!.isConnected){

        }else signtext.text="Sign Out"
    }


    fun didTapAnimabtn1(view: View) {
        mpTap.start()
        val button = findViewById<View>(R.id.animabtn1) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator


        button.startAnimation(myAnim)
    }
    fun didTapSignOut(view: View) {

        if(apiClient!!.isConnected){
        apiClient!!.disconnect()
            sharedPreference.save(SAVED_PLAY,0)
        }
        else {
            finish()
        }
        if(sharedPreference.getValueInt(SAVED_PLAY)==0){

            finish()

        }
        if(!apiClient!!.isConnected){

        }else signtext.text="Sign Out"
    }

    fun imageAnimation() {
        val imageBtn = findViewById<View>(R.id.animabtn1) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        imageBtn.startAnimation(myAnim)
        val myAnim1 = AnimationUtils.loadAnimation(this, R.anim.text)
        bestScore.startAnimation(myAnim1)
    }


}
