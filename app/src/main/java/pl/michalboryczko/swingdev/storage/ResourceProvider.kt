package pl.michalboryczko.swingdev.storage



import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.michalboryczko.swingdev.R
import javax.inject.Inject

class ResourceProvider @Inject constructor(val context: Context) {

    val communicate_no_internet = context.getString(R.string.communicate_no_internet)
    val no_sync_history = context.getString(R.string.no_sync_history)
    val last_sync = context.getString(R.string.last_sync)
    val people_on_iss = context.getString(R.string.people_on_iss)

}
