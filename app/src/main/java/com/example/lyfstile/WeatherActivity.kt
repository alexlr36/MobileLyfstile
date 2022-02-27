package com.example.lyfstile

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class WeatherActivity : AppCompatActivity(), View.OnClickListener, PassData,
    ActionbarFragment.ClickInterface {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var longitude = -111.845205
    private var latitude = 40.767778
    private val appID = "241f90adea0d5886a14c0dcfd83b5187"
    private val url = "https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=minutely,hourly,alerts,daily&appid=${appID}&units=imperial"
//    private val url = "https://google.com"
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val actionbarFragment = ActionbarFragment()
        val fragtrans = supportFragmentManager.beginTransaction()
        fragtrans.replace(R.id.action_bar_fragment, actionbarFragment, ACTION_BAR)
        fragtrans.commit()
        actionbarFragment.bindClickInterface(this)

        getWeather()
    }

    override fun onClick(view: View) {
    }

    override fun onDataPass(data: Data) {
    }

    override fun actionButtonClicked(id: Int) {
        val temp = User()
        temp.tempConstruct()
        when (id) {
            R.id.health -> {
                val healthScreen = Intent(this, HealthActivity::class.java)
                healthScreen.putExtra(USER_DATA, temp)
                // TODO - Remember to comment this back in. We need it for when we tie a database with images into this -jm
//                healthScreen.putExtra(PROFILE_PIC, profilePic)
                this.startActivity(healthScreen)
            }
            R.id.hiker -> {
                val mapScreen = Intent(this, MapActivity::class.java)
                this.startActivity(mapScreen)
            }
        }
    }

    fun getWeather() {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mURL = URL(url)

        with(mURL.openConnection() as HttpURLConnection) {
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                val weatherObject = JSONObject(response.toString())
            }
        }
    }
}