package pl.michalboryczko.swingdev.storage


import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.michalboryczko.swingdev.lib.SwingDevConstants
import javax.inject.Inject

class UserPreferences @Inject constructor(val sharedPreferences: SharedPreferences, val gson: Gson) {

	private object Key {
		const val LAST_LATITUDE = "LAST_LATITUDE"
		const val LAST_LONGITUDE = "LAST_LONGITUDE"
		const val LAST_SYNC = "LAST_SYNC"
	}

	var lastLatitude: Double
		get() = sharedPreferences.getLong(Key.LAST_LATITUDE, SwingDevConstants.DEFAULT_LATITUDE).toDouble()
		set(value) = sharedPreferences.edit().putLong(Key.LAST_LATITUDE, value.toLong()).apply()


	var lastLongitude: Double
		get() = sharedPreferences.getLong(Key.LAST_LONGITUDE, SwingDevConstants.DEFAULT_LONGITUDE).toDouble()
		set(value) = sharedPreferences.edit().putLong(Key.LAST_LONGITUDE, value.toLong()).apply()

	var lastSync: Long
		get() = sharedPreferences.getLong(Key.LAST_SYNC, SwingDevConstants.DEFAULT_SYNC)
		set(value) = sharedPreferences.edit().putLong(Key.LAST_SYNC, value).apply()

}
