package com.digosofter.poketravel.dominio;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapaItem extends DominioItem
{
  public enum EnmTipo
  {
    GINASIO,
    NONE,
    POKESHOP,
  }

  private EnmTipo _enmTipo = EnmTipo.NONE;

  public EnmTipo getEnmTipo()
  {
    return _enmTipo;
  }

  @Override
  protected BitmapDescriptor getObjIcon()
  {
    switch (this.getEnmTipo())
    {
      case GINASIO:
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);

      case POKESHOP:
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
    }

    return null;
  }

  @Override
  public String getStrNome()
  {
    switch (this.getEnmTipo())
    {
      case GINASIO:
        return "Ginásio";

      case POKESHOP:
        return "Pokéshop";
    }

    return super.getStrNome();
  }

  public void setEnmTipo(EnmTipo enmTipo)
  {
    _enmTipo = enmTipo;
  }
}