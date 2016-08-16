package com.digosofter.poketravel;

import com.digosofter.digodroid.AppAndroid;
import com.digosofter.poketravel.dominio.Viagem;
import com.google.android.gms.maps.model.LatLng;

public class AppPoketravel extends AppAndroid
{
  private static AppPoketravel i;

  public static AppPoketravel getI()
  {
    if (i != null)
    {
      return i;
    }

    i = new AppPoketravel();

    return i;
  }

  private boolean _booPararViagem = true;
  private boolean _booPausarViagem;
  private LatLng _objLatLng;
  private Viagem _objViagem;

  public boolean getBooPararViagem()
  {
    return _booPararViagem;
  }

  public boolean getBooPausarViagem()
  {
    return _booPausarViagem;
  }

  @Override
  public int getIntDrawerMenuLayoutId()
  {
    return 0;
  }

  public LatLng getObjLatLng()
  {
    return _objLatLng;
  }

  public Viagem getObjViagem()
  {
    return _objViagem;
  }

  public void setBooPararViagem(boolean booPararViagem)
  {
    _booPararViagem = booPararViagem;
  }

  public void setBooPausarViagem(boolean booPausarViagem)
  {
    _booPausarViagem = booPausarViagem;
  }

  public void setObjLatLng(LatLng objLatLng)
  {
    _objLatLng = objLatLng;
  }

  public void setObjViagem(Viagem objViagem)
  {
    _objViagem = objViagem;
  }
}