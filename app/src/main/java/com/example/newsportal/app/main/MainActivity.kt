package com.example.newsportal.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.newsportal.R
import com.example.newsportal.app.search.SearchNewsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)

                //add(R.id.fragment_container_view, TopNewsFragment())
                add(R.id.fragment_container_view, SearchNewsFragment())
            }
        }
    }
}