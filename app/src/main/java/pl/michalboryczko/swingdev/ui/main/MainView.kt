package pl.michalboryczko.swingdev.ui.main

import com.mapbox.mapboxsdk.geometry.LatLng
import pl.michalboryczko.swingdev.base.mvp.BaseView
import pl.michalboryczko.swingdev.model.LatLngPosition

interface MainView: BaseView{

    fun moveCamera(latLng: LatLng)
    fun setMarker(latLng: LatLng)
    fun setPeopleList(title: String, txt: String)
    fun setSyncData(txt: String)

}