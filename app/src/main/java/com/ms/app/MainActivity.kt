package com.ms.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.Mavericks

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mavericks.initialize(application)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, MainFragment()).commit()



    }
}