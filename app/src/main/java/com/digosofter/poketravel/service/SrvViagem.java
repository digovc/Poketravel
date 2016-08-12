package com.digosofter.poketravel.service;

import android.location.Location;
import android.os.SystemClock;

import com.digosofter.digodroid.service.ServiceMain;
import com.digosofter.digojava.Utils;
import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.dominio.Viagem;
import com.digosofter.poketravel.dominio.ViagemItem;
import com.google.android.gms.maps.model.LatLng;

public class SrvViagem extends ServiceMain
{
  private static final double DBL_VELOCIDADE_28_8_KM_H = 0.01d;
  private static final double DBL_VELOCIDADE_TEST = 0.00005d;
  private static final double DBL_VELOCIDADE = DBL_VELOCIDADE_TEST;
  private static final int INT_INTERVALO_MILISEGUNDO = 1000;
  private GpsProvider _gpsProviderGps;
  private GpsProvider _gpsProviderNetwork;
  private LatLng _objLatLngProximo;
  private Viagem _objViagem;

  public SrvViagem()
  {
    super("Serviço de GPS Poketravel");
  }

  private void atualizarLocalizacao()
  {
    //    this.getGpsProviderGps().setObjLatLng(AppPoketravel.getI().getObjLatLng());
    this.getGpsProviderNetwork().setObjLatLng(AppPoketravel.getI().getObjLatLng());
  }

  private void calcularObjLatLng()
  {
    double dblLatitudeDiff = (this.getObjLatLngProximo().latitude - AppPoketravel.getI().getObjLatLng().latitude);
    double dblLongitudeDiff = (this.getObjLatLngProximo().longitude - AppPoketravel.getI().getObjLatLng().longitude);

    double dblDiffTotal = (Math.abs(dblLatitudeDiff) + Math.abs(dblLongitudeDiff));

    double dblLatitudeDiffPerc = (Math.abs(dblLatitudeDiff) / dblDiffTotal);
    double dblLongitudeDiffPerc = (Math.abs(dblLongitudeDiff) / dblDiffTotal);

    double dblLatitudeDistancia = (DBL_VELOCIDADE * dblLatitudeDiffPerc);
    double dblLongitudeDistancia = (DBL_VELOCIDADE * dblLongitudeDiffPerc);

    if (dblLatitudeDistancia > Math.abs(dblLatitudeDiff))
    {
      AppPoketravel.getI().setObjLatLng(new LatLng(this.getObjLatLngProximo().latitude, this.getObjLatLngProximo().longitude));
      return;
    }

    if (dblLongitudeDistancia > Math.abs(dblLongitudeDiff))
    {
      AppPoketravel.getI().setObjLatLng(new LatLng(this.getObjLatLngProximo().latitude, this.getObjLatLngProximo().longitude));
      return;
    }

    if (this.getObjLatLngProximo().latitude < AppPoketravel.getI().getObjLatLng().latitude)
    {
      dblLatitudeDistancia *= -1;
    }

    if (this.getObjLatLngProximo().longitude < AppPoketravel.getI().getObjLatLng().longitude)
    {
      dblLongitudeDistancia *= -1;
    }

    double dblLatitudeFinal = (AppPoketravel.getI().getObjLatLng().latitude + dblLatitudeDistancia);
    double dblLongitudeFinal = (AppPoketravel.getI().getObjLatLng().longitude + dblLongitudeDistancia);

    AppPoketravel.getI().setObjLatLng(new LatLng(dblLatitudeFinal, dblLongitudeFinal));
  }

  private void calcularObjLatLngProximo()
  {
    if (this.getObjLatLngProximo() == null)
    {
      this.setObjLatLngProximo(this.getObjViagem().getArrObjViagemItem().get(1).getObjLatLng());
      return;
    }

    Location objLocationAtual = new Location(Utils.STR_VAZIA);

    objLocationAtual.setLatitude(AppPoketravel.getI().getObjLatLng().latitude);
    objLocationAtual.setLongitude(AppPoketravel.getI().getObjLatLng().longitude);

    Location objLocationProximo = new Location(Utils.STR_VAZIA);

    objLocationProximo.setLatitude(this.getObjLatLngProximo().latitude);
    objLocationProximo.setLongitude(this.getObjLatLngProximo().longitude);

    if (objLocationAtual.distanceTo(objLocationProximo) > 1)
    {
      return;
    }

    int intIndex = 0;

    for (ViagemItem objViagemItem : this.getObjViagem().getArrObjViagemItem())
    {
      if (objViagemItem == null)
      {
        continue;
      }

      if (!objViagemItem.getObjLatLng().toString().equals(this.getObjLatLngProximo().toString()))
      {
        continue;
      }

      intIndex = (this.getObjViagem().getArrObjViagemItem().indexOf(objViagemItem) + 1);
      break;
    }

    if (this.getObjViagem().getArrObjViagemItem().size() <= intIndex)
    {
      intIndex = 0;
    }

    this.setObjLatLngProximo(this.getObjViagem().getArrObjViagemItem().get(intIndex).getObjLatLng());
  }

  private GpsProvider getGpsProviderGps()
  {
    if (_gpsProviderGps != null)
    {
      return _gpsProviderGps;
    }

    _gpsProviderGps = new GpsProvider(this, GpsProvider.EnmTipo.GPS);

    return _gpsProviderGps;
  }

  private GpsProvider getGpsProviderNetwork()
  {
    if (_gpsProviderNetwork != null)
    {
      return _gpsProviderNetwork;
    }

    _gpsProviderNetwork = new GpsProvider(this, GpsProvider.EnmTipo.NETWORK);

    return _gpsProviderNetwork;
  }

  private LatLng getObjLatLngProximo()
  {
    return _objLatLngProximo;
  }

  private Viagem getObjViagem()
  {
    if (_objViagem != null)
    {
      return _objViagem;
    }

    _objViagem = AppPoketravel.getI().getObjViagem();

    return _objViagem;
  }

  @Override
  protected void inicializar()
  {
    super.inicializar();

    //    this.getGpsProviderGps().iniciar();
    this.getGpsProviderNetwork().iniciar();
  }

  private void loop()
  {
    this.calcularObjLatLngProximo();
    this.calcularObjLatLng();
    this.atualizarLocalizacao();

    SystemClock.sleep(INT_INTERVALO_MILISEGUNDO);
  }

  @Override
  protected void servico()
  {
    super.servico();

    while (!this.getBooParar())
    {
      this.loop();
    }
  }

  private void setObjLatLngProximo(LatLng objLatLngProximo)
  {
    _objLatLngProximo = objLatLngProximo;
  }

  @Override
  protected boolean validarDados()
  {
    if (!super.validarDados())
    {
      return false;
    }

    if (this.getObjViagem() == null)
    {
      return false;
    }

    if (this.getObjViagem().getArrObjViagemItem() == null)
    {
      return false;
    }

    if (this.getObjViagem().getArrObjViagemItem().size() < 2)
    {
      return false;
    }

    if (AppPoketravel.getI().getObjLatLng() == null)
    {
      return false;
    }

    return true;
  }

  private void verificarStatus()
  {
    if (!AppPoketravel.getI().getBooPararViagem())
    {
      return;
    }

    this.setBooParar(true);
  }
}