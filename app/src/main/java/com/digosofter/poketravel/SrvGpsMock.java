package com.digosofter.poketravel;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.digosofter.digodroid.service.ServiceMain;

public class SrvGpsMock extends ServiceMain
{
  private final String STR_GPS_PROVIDER_NOME = LocationManager.GPS_PROVIDER;
  private LocationManager _objLocationManager;

  public SrvGpsMock()
  {
    super("Serviço de GPS Poketravel");
  }

  private void atualizarGps()
  {
    Location objLocation = new Location(STR_GPS_PROVIDER_NOME);

    objLocation.setAccuracy(5);
    objLocation.setAltitude(0);
    objLocation.setBearing(0);
    objLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
    objLocation.setLatitude(AppPoketravel.getI().getObjLatLng().latitude);
    objLocation.setLongitude(AppPoketravel.getI().getObjLatLng().longitude);
    objLocation.setTime(System.currentTimeMillis());

    try
    {
      this.getObjLocationManager().setTestProviderLocation(STR_GPS_PROVIDER_NOME, objLocation);
    }
    catch (IllegalArgumentException ex)
    {
      Log.d("Teste", "Erro ao tentar atualizar o local");
    }
  }

  private void calcularGps()
  {

  }

  @Override
  protected void finalizar()
  {
    super.finalizar();

    if (this.getObjLocationManager() == null)
    {
      return;
    }

    if (this.getObjLocationManager().getAllProviders() == null)
    {
      return;
    }

    if (!this.getObjLocationManager().getAllProviders().contains(STR_GPS_PROVIDER_NOME))
    {
      return;
    }

    //    this.getObjLocationManager().removeTestProvider(STR_GPS_PROVIDER_NOME);
  }

  private LocationManager getObjLocationManager()
  {
    if (_objLocationManager != null)
    {
      return _objLocationManager;
    }

    _objLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    if (_objLocationManager == null)
    {
      return null;
    }

    if (_objLocationManager.getAllProviders() != null && !_objLocationManager.getAllProviders().contains(STR_GPS_PROVIDER_NOME))
    {
      _objLocationManager.addTestProvider(STR_GPS_PROVIDER_NOME, false, false, false, false, false, true, true, Criteria.NO_REQUIREMENT, android.location.Criteria.ACCURACY_FINE);
      _objLocationManager.setTestProviderEnabled(STR_GPS_PROVIDER_NOME, true);
    }

    return _objLocationManager;
  }

  @Override
  protected boolean inicializar()
  {
    if (!super.inicializar())
    {
      return false;
    }

    if (AppPoketravel.getI().getObjViagem() == null)
    {
      return false;
    }

    this.inicializarGps();

    return true;
  }

  private void inicializarGps()
  {
    //    this.getObjLocationManager();
  }

  private void loop()
  {
    if (this.getObjLocationManager() == null)
    {
      this.setBooParar(true);
      return;
    }

    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    try
    {
      Log.d("Localização:", this.getObjLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER).toString());
      Log.d("Localização:", this.getObjLocationManager().getLastKnownLocation(LocationManager.NETWORK_PROVIDER).toString());
      Log.d("Localização:", this.getObjLocationManager().getLastKnownLocation(LocationManager.PASSIVE_PROVIDER).toString());
    }
    catch (Exception ex)
    {

    }

    //    this.atualizarGps();
    //    this.calcularGps();
    //    this.verificarStatus();
  }

  @Override
  protected void servico()
  {
    super.servico();

    while (!this.getBooParar())
    {
      this.loop();

      try
      {
        Thread.sleep(1500);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    this.finalizar();
  }

  private void verificarStatus()
  {
    this.setBooParar(AppPoketravel.getI().getBooPararViagem());
  }
}