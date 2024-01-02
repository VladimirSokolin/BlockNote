package com.bignerdranch.android.blocknote

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor
import androidx.fragment.app.Fragment
import java.sql.Time
import kotlin.system.measureTimeMillis

class ChronometerFragment: Fragment() {

    private lateinit var btStart: Button
    private lateinit var btStop: Button
    private lateinit var chronometer: Chronometer
    private lateinit var tvOne: TextView
    private lateinit var tvTwo: TextView
    private lateinit var contained: ViewGroup


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hronometr, container, false)

        btStart = view.findViewById(R.id.bt_start)
        btStop = view.findViewById(R.id.bt_stop)
        chronometer = view.findViewById(R.id.chronometer2)
        tvOne = view.findViewById(R.id.tvOne)
        contained = view.findViewById(R.id.constraint)

        return view
    }
    private var base1: Long  = 150000


    override fun onStart() {
        super.onStart()
        btStart.setOnClickListener{
            chronometer.base = base1
            chronometer.start()

        }
        btStop.setOnClickListener{
            chronometer.stop()
            tvOne.text = chronometer.base.toString()
            base1 = chronometer.base

        }

    }


}