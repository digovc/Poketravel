package com.digosofter.poketravel.service;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.digosofter.digojava.Objeto;
import com.google.android.gms.maps.model.LatLng;

public class GpsProvider extends Objeto
{
  public enum EnmTipo
  {
    GPS,
    NETWORK,
    NONE,
  }

  private EnmTipo _enmTipo;
  private LatLng _objLatLng;
  private LocationManager _objLocationManager;
  private SrvViagem _srvViagem;
  private String _strLocationProviderName;

  GpsProvider(SrvViagem srvViagem, final EnmTipo enmTipo)
  {
    this.setEnmTipo(enmTipo);
    this.setSrvViagem(srvViagem);
  }

  private void atualizarObjLatLng()
  {
    if (this.getObjLatLng() == null)
    {
      return;
    }

    Location objLocation = new Location("gps");

    if (Build.VERSION.SDK_INT >= 17)
    {
      objLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    }

    objLocation.setAccuracy(0.001f);
    objLocation.setAltitude(0.0d);
    objLocation.setBearing(0.0f);
    objLocation.setLatitude(this.getObjLatLng().latitude);
    objLocation.setLongitude(this.getObjLatLng().longitude);
    objLocation.setTime(System.currentTimeMillis());

    try
    {
      this.getObjLocationManager().setTestProviderLocation(this.getStrLocationProviderName(), objLocation);
      Log.d("Localização", this.getObjLatLng().toString());
    }
    catch (IllegalArgumentException ex)
    {
      Log.d("Teste", "Erro ao tentar atualizar o local");
    }
  }

  void finalizar()
  {
    if (!this.getObjLocationManager().isProviderEnabled(this.getStrLocationProviderName()))
    {
      return;
    }

    this.getObjLocationManager().removeTestProvider(this.getStrLocationProviderName());
  }

  private EnmTipo getEnmTipo()
  {
    return _enmTipo;
  }

  private LatLng getObjLatLng()
  {
    return _objLatLng;
  }

  private LocationManager getObjLocationManager()
  {
    return _objLocationManager;
  }

  private SrvViagem getSrvViagem()
  {
    return _srvViagem;
  }

  private String getStrLocationProviderName()
  {
    if (_strLocationProviderName != null)
    {
      return _strLocationProviderName;
    }

    switch (this.getEnmTipo())
    {
      case GPS:
        return _strLocationProviderName = LocationManager.GPS_PROVIDER;

      case NETWORK:
        return _strLocationProviderName = LocationManager.NETWORK_PROVIDER;
    }

    return _strLocationProviderName;
  }

  private void inicializar()
  {
    this.inicializarObjLocationManager();
  }

  private void inicializarObjLocationManager()
  {
    this.setObjLocationManager((LocationManager) this.getSrvViagem().getSystemService(Context.LOCATION_SERVICE));

    if (this.getObjLocationManager() == null)
    {
      return;
    }

    this.getObjLocationManager().addTestProvider(this.getStrLocationProviderName(), false, false, false, false, false, false, false, Criteria.NO_REQUIREMENT, android.location.Criteria.ACCURACY_FINE);

    if (!this.getObjLocationManager().isProviderEnabled(this.getStrLocationProviderName()))
    {
      this.getObjLocationManager().setTestProviderEnabled(this.getStrLocationProviderName(), true);
    }
  }

  void iniciar()
  {
    this.inicializar();
    this.setEventos();
  }

  private void setEnmTipo(EnmTipo enmTipo)
  {
    _enmTipo = enmTipo;
  }

  private void setEventos()
  {

  }

  void setObjLatLng(LatLng objLatLng)
  {
    if (_objLatLng == objLatLng)
    {
      return;
    }
    _objLatLng = objLatLng;

    this.atualizarObjLatLng();
  }

  private void setObjLocationManager(LocationManager objLocationManager)
  {
    _objLocationManager = objLocationManager;
  }

  private void setSrvViagem(SrvViagem srvViagem)
  {
    _srvViagem = srvViagem;
  }
}