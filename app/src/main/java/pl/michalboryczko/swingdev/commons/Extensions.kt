package pl.michalboryczko.swingdev.commons


import java.text.SimpleDateFormat
import java.util.*



fun Long.toPrintableDate(): String{
	val date = Date(this * 1000L)
	val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
	return sdf.format(date)
}

