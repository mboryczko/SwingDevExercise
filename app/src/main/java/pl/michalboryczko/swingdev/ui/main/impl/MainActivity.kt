package pl.michalboryczko.swingdev.ui.main.impl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.activity_main.*
import pl.michalboryczko.swingdev.R
import pl.michalboryczko.swingdev.base.BaseActivity
import pl.michalboryczko.swingdev.ui.main.MainPresenter
import pl.michalboryczko.swingdev.ui.main.MainView
import javax.inject.Inject
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import android.graphics.BitmapFactory
import com.mapbox.mapboxsdk.annotations.Icon
import pl.michalboryczko.swingdev.lib.SwingDevConstants


class MainActivity : BaseActivity(), MainView{

	@Inject
	lateinit var presenter: MainPresenter

	lateinit var mapboxMap: MapboxMap
	var currentMarker: Marker? = null
	var rocketIcon: Icon? = null

	var isMarkerBubbleVisible: Boolean = false


	companion object {
		fun prepareIntent(activity: Activity): Intent = Intent(activity, MainActivity::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activityComponent.inject(this)
		presenter.attachView(this)
		setContentView(R.layout.activity_main)
		mapView.onCreate(savedInstanceState)

		if(savedInstanceState != null){
			isMarkerBubbleVisible = savedInstanceState.getBoolean(SwingDevConstants.IS_MARKER_VISIBLE)
		}

	}


	private fun getMapAsynchAndSetCallbacks(){
		mapView.getMapAsync {
			mapboxMap = it
			rocketIcon = prepareIcon()

			mapboxMap.setOnMarkerClickListener {
				isMarkerBubbleVisible = !isMarkerBubbleVisible
				presenter.onMarkerClicked()
				true
			}

			presenter.onMapReady()
		}
	}

	private fun prepareIcon(): Icon {
		val iconFactory = IconFactory.getInstance(this)
		val bitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket)
		return iconFactory.fromBitmap(bitmap)
	}

	override fun setSyncData(txt: String) {
		lastSyncTextView.text = txt
	}

	override fun setPeopleList(title: String, txt: String) {
		currentMarker?.title = title
		currentMarker?.snippet = txt

		if(isMarkerBubbleVisible)
			currentMarker?.showInfoWindow(mapboxMap, mapView)
		else
			currentMarker?.hideInfoWindow()
	}

	override fun moveCamera(latLng: LatLng) {
		val position = CameraPosition.Builder()
				.target(latLng)
				.build()

		mapboxMap?.animateCamera(CameraUpdateFactory
				.newCameraPosition(position), 1500)


	}

	override fun setMarker(latLng: LatLng) {
		currentMarker?.remove()
		currentMarker = mapboxMap.addMarker(MarkerOptions()
				.position(latLng)
				.icon(rocketIcon)
		)

	}

	public override fun onStart() {
		super.onStart()
		mapView.onStart()
	}

	public override fun onResume() {
		super.onResume()
		mapView.onResume()
		getMapAsynchAndSetCallbacks()
		presenter.resume()
	}

	public override fun onPause() {
		super.onPause()
		mapView.onPause()
		presenter.pause()
	}

	public override fun onStop() {
		super.onStop()
		mapView.onStop()
	}

	override fun onLowMemory() {
		super.onLowMemory()
		mapView.onLowMemory()
	}

	override fun onDestroy() {
		super.onDestroy()
		mapView.onDestroy()
		presenter.destroy()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		mapView.onSaveInstanceState(outState)
		outState.putBoolean(SwingDevConstants.IS_MARKER_VISIBLE, isMarkerBubbleVisible);
	}

}

