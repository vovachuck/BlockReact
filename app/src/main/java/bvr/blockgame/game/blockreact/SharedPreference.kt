package bvr.blockgame.game.blockreact

import android.app.backup.BackupAgentHelper
import android.content.Context
import android.content.SharedPreferences
import android.app.backup.BackupManager.dataChanged
import android.app.backup.BackupManager
import android.content.Context.MODE_PRIVATE
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import java.util.*
import kotlin.collections.ArrayList


class SharedPreference(val context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }
    fun save(KEY_NAME: String, value: ArrayList<Int>) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, value.joinToString())

        editor.commit()
    }
    fun getValueInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 80)
    }
    fun getValueScoreMoney(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueArray(KEY_NAME: String): ArrayList<Int> {
        lateinit var savedList : ArrayList<Int>
        var list=sharedPref.getString(KEY_NAME, "")
        if(list!=""){
        var result: List<String> = list.split(",").map { it.trim() }
        savedList=ArrayList(result.map { it.toInt() })
        return savedList}
        savedList= ArrayList(0)
        return savedList
    }



    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_NAME)
        editor.commit()
    }

    fun requestBackup() {
        val bm = BackupManager(context)
        bm.dataChanged()
    }
}
