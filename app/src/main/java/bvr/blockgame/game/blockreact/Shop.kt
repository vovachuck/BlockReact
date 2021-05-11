package bvr.blockgame.game.blockreact

import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_shop.*
import android.widget.*
import android.os.Handler
import java.util.*
import android.widget.Toast
import bvr.blockgame.game.blockreact.R.id.*


class Shop : AppCompatActivity() {
    val SAVED_COIN = "coin"
    val SAVED_ARRAY = "array"
    val SAVED_SKIN = "skin"
    private var skinBuy: ArrayList<Int> = ArrayList()
    private lateinit var sharedPreference:SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_shop)
        val bounds = Rect()
        skinroll4.getHitRect(bounds)

        val scrollBounds = Rect(scrollView.getScrollX(), scrollView.getScrollY(),
                scrollView.getScrollX() + scrollView.getWidth(), scrollView.getScrollY() + scrollView.getHeight())


       /* if (skinroll4.getLocalVisibleRect(scrollBounds)) {
            // Any portion of the imageView, even a single pixel, is within the visible window
            Toast.makeText(this, winline.x.toString()+" skin "+skinroll4.x.toString(),
                    Toast.LENGTH_LONG).show()
            if(skinroll4.x>winline.x-5&& skinroll4.x<winline.x+5)
            {
                Toast.makeText(this, "win",
                        Toast.LENGTH_LONG).show()
            }
        } else {
            // NONE of the imageView is within the visible window
        }*/
        sharedPreference=SharedPreference(this)
       moneyShop.text=sharedPreference.getValueScoreMoney(SAVED_COIN).toString()

        skinBuy=sharedPreference.getValueArray(SAVED_ARRAY)
        println(skinBuy)
        if(skinBuy.indexOf(1)!=-1){
            skin1.setImageResource(0)
        }
        if(skinBuy.indexOf(2)!=-1){
            skin2.setImageResource(0)
        }
        if(skinBuy.indexOf(3)!=-1){
            skin3.setImageResource(0)
        }
        if(skinBuy.indexOf(4)!=-1){
            skin4.setImageResource(0)
        }
        if(skinBuy.indexOf(5)!=-1){
            skin5.setImageResource(0)
        }
        if(skinBuy.indexOf(6)!=-1){
            skin6.setImageResource(0)
        }
    }
    fun didTapExit(view: View) {
        val button = findViewById<View>(R.id.exit) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        val gameAct = Intent (this, Main::class.java)
        finish()
        startActivity(gameAct)
    }
    var scrollAm=100
    var endScroll=0

    //To stop timer
    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
        }
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private val handler = Handler()
    /*private fun startTimer() {
        val sv=findViewById<View>(R.id.scrollView) as HorizontalScrollView
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                   scrollAm += 80
                    sv.scrollTo(scrollAm,0)
                    if (scrollAm>=endScroll){
                        stopTimer()
                    }

                })
            }
        }
        timer!!.schedule(timerTask, 100, 10)}*/
   /* fun didTapSpin(view: View) {
        val button = findViewById<View>(R.id.spin) as Button
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
        spinlayout.setVisibility(View.VISIBLE)
        skinlayout.visibility=View.GONE
        endScroll+=2000
      startTimer()

    }*/
    val ok=false
    var skin=0


    val SKIN_1=100
    val SKIN_2=100
    val SKIN_3=500
    val SKIN_4=500
    val SKIN_5=500
    val SKIN_6=500


    fun didTapExitSpin(view: View) {
        spinlayout.setVisibility(View.GONE)
        skinlayout.visibility=View.VISIBLE
    }
    fun didTapSkin1(view: View) {

        skin=1

        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_1.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,1)

        }
    }
    fun didTapSkin2(view: View) {


        skin=2
        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_2.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,2)

        }
    }
    fun didTapSkin3(view: View) {

        skin=3
        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_3.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,3)

        }
    }
    fun didTapSkin4(view: View) {

        skin=4
        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_4.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,4)

        }
    }
    fun didTapSkin5(view: View) {

        skin=5
        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_5.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,5)

        }
    }
    fun didTapSkin6(view: View) {

        skin=6
        if (skinBuy.indexOf(skin)==-1){
            amount.text=SKIN_6.toString()
        approve.visibility=View.VISIBLE
        }
        else {
            sharedPreference.save(SAVED_SKIN,6)

        }
    }
    fun didTapOk(view: View) {
        when(skin){
            0->{

            }
            1->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_1){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_1)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(1)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin1.setImageResource(0)
            }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            2->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_2){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_2)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(2)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin2.setImageResource(0)
                }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            3->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_3){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_3)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(3)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin3.setImageResource(0)
                }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            4->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_4){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_4)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(4)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin4.setImageResource(0)
                }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            5->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_5){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_5)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(5)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin5.setImageResource(0)
                }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            6->{
                if(sharedPreference.getValueInt(SAVED_COIN)>SKIN_6){
                    sharedPreference.save(SAVED_COIN,sharedPreference.getValueInt(SAVED_COIN)-SKIN_6)
                    moneyShop.text=sharedPreference.getValueInt(SAVED_COIN).toString()
                    skinBuy.add(6)
                    sharedPreference.save(SAVED_ARRAY,skinBuy)
                    skin6.setImageResource(0)
                }
                else {
                    val toast = Toast.makeText(applicationContext,
                            "Not enough money", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
        }
        approve.visibility=View.GONE
        skin=0
    }
    fun didTapCancel(view: View) {
        approve.visibility=View.GONE
        skin=0
    }

}
