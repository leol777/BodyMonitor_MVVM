package liang.leo.bodymonitor.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import liang.leo.bodymonitor.util.PermissionUtil

class LocationModel {
    var locationManager:LocationManager? = null
    val provider:String = LocationManager.GPS_PROVIDER;
    var context:Context

    private constructor(context: Context){
        this.context = context
        val serviceName:String = Context.LOCATION_SERVICE
        locationManager = context.getSystemService(serviceName) as LocationManager
    }


    companion object{
        var instance:LocationModel? = null

        fun getInstance(context: Context) : LocationModel{
            if(instance == null){
                instance = LocationModel(context)
            }
            return instance!!
        }
    }


    @SuppressLint("MissingPermission")
    fun getLatitude(): Double? {
        if(PermissionUtil.handlePermission(context, Manifest.permission.INTERNET)
            && PermissionUtil.handlePermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            && PermissionUtil.handlePermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)){
            return locationManager?.getLastKnownLocation(provider)?.latitude
        }else{
            return null
        }
    }

    @SuppressLint("MissingPermission")
    fun getLongitude(): Double? {
        if(PermissionUtil.handlePermission(context, Manifest.permission.INTERNET)
            && PermissionUtil.handlePermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            && PermissionUtil.handlePermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)){
            return locationManager?.getLastKnownLocation(provider)?.longitude
        }else{
            return null
        }
    }
}