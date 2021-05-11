package bvr.blockgame.game.blockreact

import java.util.*
import kotlin.collections.ArrayList

class   ButtonGenerate () {
    var positionX:Float=0.0f
    var positionY:Float=0.0f
  //  var timeLife:Int=0
    var colorBtn:String="default"
    var typeBtn:String="default"
    var destroyed:Boolean=false
    var size:Float=1.0f
    var anim:Boolean=false
    /*private var buttonWidth:Int=0
    private var buttonHeight:Int=0*/

    fun generateSize (width :Int, height :Int, listBtn:ArrayList<ButtonGenerate>){



        var xTestPosition= generateX(width)
        var yTestPosition= generateY(height)
        var flag=true
        var flagXtest=false
        var flagYtest=false
        while (flag){
        for (i in listBtn) {
            println("-------------")
            if(i.positionY-70<yTestPosition && yTestPosition<i.positionY+70){
                yTestPosition=generateY(height)
            }else { flagYtest=true}
            if(i.positionX-70<xTestPosition && xTestPosition<i.positionX+70){
                xTestPosition=generateX(width)

            }else { flagYtest=true}
            println(xTestPosition)
            println(yTestPosition)

        }
            if (listBtn.count()==0){flag=false
            }

            if(flagXtest || flagYtest)flag=false
        }
        positionX= xTestPosition
        positionY= yTestPosition

    }
    fun generateX (width :Int):Float{
        var leftLimitX = 100f
        var rightLimitX = width-200
        return leftLimitX + Random().nextFloat() * (rightLimitX - leftLimitX)
    }
    fun generateY (height :Int):Float{
        var leftLimitY = 100f
        var rightLimitY = height-150
        return leftLimitY + Random().nextFloat() * (rightLimitY - leftLimitY)
    }
    fun generateColor(): Int {

        when (colorBtn) {
            "default" ->{
                var endInclusive=1
                var start=11
                val random = Random()
                var randNumb= random.nextInt(start - endInclusive) + endInclusive
                when(randNumb){
                    4,5,6,7-> {colorBtn="blue"
                        return R.drawable.blue_button11}
                    1,2,3-> {colorBtn="green"
                        return R.drawable.green_button11}
                    10-> {colorBtn="yellow"
                            return R.drawable.yellow_button11}
                    8,9-> {colorBtn="red"
                                return R.drawable.red_button08}
                }
            }
            "blue" -> return R.drawable.blue_button11
            "green" ->return R.drawable.green_button11
            "yellow" ->return R.drawable.yellow_button11
            "red" ->return R.drawable.red_button08
        }
        return 0
    }
    fun generateType(spawn2x:Boolean): Int {

        when (typeBtn) {
            "default" ->{
                var endInclusive=1
                var start=11
                if(!spawn2x)
                    start=11
                else if(spawn2x)start=10

                val random = Random()
                var randNumb= random.nextInt(start - endInclusive) + endInclusive
                when(randNumb){
                    9-> {typeBtn="coin"
                        return R.drawable.coin}
                    10-> {typeBtn="2x"


                        return R.drawable.letterx}
                    8,7-> {typeBtn="stop"
                        return R.drawable.stop}
                    1,2,3,4,5,6-> {typeBtn="default"
                        return 0}
                }
            }
            "coin" ->return R.drawable.coin
            "2x" ->return R.drawable.letterx
            "stop" ->return R.drawable.stop

        }

        return 0
    }

}