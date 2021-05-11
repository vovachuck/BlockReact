package bvr.blockgame.game.blockreact

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import kotlinx.android.synthetic.main.activity_game.*
import android.widget.RelativeLayout
import java.util.*
import android.graphics.Point
import android.widget.ImageButton
import kotlin.concurrent.schedule
import android.os.CountDownTimer
import android.media.MediaPlayer
import android.util.Log
import com.google.android.gms.ads.MobileAds


class Game : AppCompatActivity() {
    var pauseStatus="play"
    var soundStatus="soundOn"
    var delay:Long=3000
    val SAVED_SOUND = "sound"
    val SAVED_MUSIC = "music"
    private var score=0
    private var coin=0
    private var active2x:Int=1
    var spawn2x=false
    var finish=false
    val SAVED_SKIN = "skin"
    lateinit var mpTap: MediaPlayer
    private lateinit var sharedPreference:SharedPreference
    val SAVED_TEXT = "coin"
    private lateinit var context :Context

    val listBtn = ArrayList<ButtonGenerate>()
    override fun onCreate(savedInstanceState: Bundle?) {
        mpTap = MediaPlayer.create(this, R.raw.click1)


        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game)
        MobileAds.initialize(this,"ca-app-pub-7026303595758317~5996350025")
        sharedPreference=SharedPreference(this)
        mpTap.setVolume(sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100,
                sharedPreference.getValueInt(SAVED_SOUND).toFloat()/100)
        when(sharedPreference.getValueInt(SAVED_SKIN)){
            1->{
                maingame.setBackgroundResource(R.drawable.background1)

            }
            2->{
                maingame.setBackgroundResource(R.drawable.background2)
            }
            3->{
                maingame.setBackgroundResource(R.drawable.background3)
            }
            4->{
                maingame.setBackgroundResource(R.drawable.background4)
            }
            5->{
                maingame.setBackgroundResource(R.drawable.background5)
            }
            6->{
                maingame.setBackgroundResource(R.drawable.background6)
            }

        }
        context=this
        info.setOnClickListener {
            info.visibility=View.GONE
            timeStart()
        }





    }
    lateinit var waitTimer1: CountDownTimer
    lateinit var waitTimer: CountDownTimer
    lateinit var timer:TimerTask

     private fun timeStart(){

         waitTimer1 = object : CountDownTimer(delay, 300) {

            override fun onTick(millisUntilFinished: Long) {
                //called every 300 milliseconds, which could be used to
                //send messages or some other action
            }

            override fun onFinish() {
                //After 60000 milliseconds (60 sec) finish current
                //if you would like to execute something when time finishes
                generateBtn()

            }
        }.start()

    }
    override fun onPause() {
        super.onPause()

      /*  waitTimer1.cancel()
        waitTimer.cancel()*/

    }
    var flag=true

    fun generateBtn (){

        if(delay>=2000)
        delay=delay-500
        else if(delay>=1000)
            delay=delay-200
        else if(delay>=500)
            delay=delay-100
        else if(delay>=400)
            delay=delay-10
        else if(delay>=300)
            delay=delay-10
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        var firstBtn =ButtonGenerate()
        firstBtn.colorBtn="default"
        firstBtn.typeBtn="default"
        val b = ImageButton(this)
        val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        b.setBackgroundResource(firstBtn.generateColor())
        var spawn=firstBtn.generateType(spawn2x)
        if(spawn==R.drawable.letterx)spawn2x=true
        b.setImageResource(spawn)

        firstBtn.generateSize(width,height,listBtn)

        b.translationX=firstBtn.positionX
        b.translationY=firstBtn.positionY

        var vi:View=b.rootView
        b.setOnClickListener {
            v -> buttonlayout.removeView(v)
            mpTap.start()
            firstBtn.destroyed=true
            if(firstBtn.typeBtn=="coin"){

                sharedPreference.save(SAVED_TEXT,sharedPreference.getValueScoreMoney(SAVED_TEXT)+1)

            }
            if(firstBtn.typeBtn=="2x"){
                active2x=2
                timer=Timer().schedule(10000){
                    active2x=1
                    spawn2x=false




                }
            }
            if(firstBtn.typeBtn=="stop"){
                val gameAct = Intent (this, Loose::class.java)

                gameAct.putExtra(Loose.TOTAL_SCORE,score)
                finish=true
                waitTimer1.cancel()
                waitTimer.cancel()

                startActivity(gameAct)
                finish()
            }
            else if(firstBtn.colorBtn=="green")score=score+2*active2x
            else if(firstBtn.colorBtn=="blue")score=score+1*active2x
            else if(firstBtn.colorBtn=="yellow")score=score+3*active2x
            else if (firstBtn.colorBtn=="red")score=score-1*active2x
            digit.text=score.toString()

        }
        if(active2x==2)bonus.setVisibility(View.VISIBLE)
        else if(active2x!=2)bonus.setVisibility(View.INVISIBLE)
        val shake = AnimationUtils.loadAnimation(this, R.anim.gamebtn)

        waitTimer = object : CountDownTimer(3000, 300) {

            override fun onTick(millisUntilFinished: Long) {

                if(!firstBtn.anim){
                    b.animate().duration=3000
                    b.animate().scaleX(0.5f)
                    b.animate().scaleY(0.5f)
                    firstBtn.anim=true

                }


            }


            override fun onFinish() {

                if(firstBtn.typeBtn=="stop"||firstBtn.colorBtn=="red" ||firstBtn.destroyed) {
                    flag = true

                }
                else {
                    if(!finish) {
                        flag = false
                        Log.d("onFinishTimer", "Can't stop.")
                        println("------------------------------adwadwadaw--------------")
                        cancelTimer()
                        finish = true
                        val gameAct = Intent(context, Loose::class.java)
                        flag = true
                        gameAct.putExtra(Loose.TOTAL_SCORE, score)
                        startActivity(gameAct)
                        finish()
                    }
                }
                buttonlayout.removeView(vi)

            }
        }.start()
        listBtn.add(firstBtn)
        if(!flag){

                waitTimer.cancel()


            waitTimer1.cancel()
        finish=true
        val gameAct = Intent (this, Loose::class.java)
        flag=true
            gameAct.putExtra(Loose.TOTAL_SCORE,score)
        startActivity(gameAct)
            finish()
        }
        timeStart()
        buttonlayout.addView(b, lp)
    }
//end of fun
fun cancelTimer() {
    if (waitTimer != null)
        waitTimer.cancel()
    if (waitTimer1 != null)
        waitTimer1.cancel()
}

    fun tapPause(view: View) {
        if(pauseStatus=="play"){
            pause.setImageResource(R.drawable.playgame)
            pausemenu.setVisibility(View.VISIBLE)
            waitTimer.cancel()
            waitTimer1.cancel()

            pauseStatus="pause"
        }
        else if (pauseStatus=="pause"){
            pause.setImageResource(R.drawable.pausegame)
            pausemenu.setVisibility(View.GONE)
            waitTimer.start()
            waitTimer1.start()
            pauseStatus="play"
        }



    }

    fun tapResume(view: View) {
        val button = findViewById<View>(R.id.resume) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        pause.setImageResource(R.drawable.pausegame)
        pausemenu.setVisibility(View.GONE)
    }

    fun tapMenu(view: View) {
        val button = findViewById<View>(R.id.mainmenu) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        pausemenu.setVisibility(View.GONE)
        finish()
        val gameAct = Intent (this, Main::class.java)
        startActivity(gameAct)
    }

    fun tapRestart(view: View) {
        val button = findViewById<View>(R.id.restart) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        pausemenu.setVisibility(View.GONE)

        startActivity(getIntent())
        finish()
    }

    fun tapSound(view: View) {
        if(soundStatus=="soundOn"){
            sound.setImageResource(R.drawable.soundmute)

            soundStatus="soundOff"
        }
        else if (soundStatus=="soundOff"){
            sound.setImageResource(R.drawable.soundon)

            soundStatus="soundOn"
        }

    }
}
