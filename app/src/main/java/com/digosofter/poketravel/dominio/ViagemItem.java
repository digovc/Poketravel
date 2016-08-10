package com.digosofter.poketravel.dominio;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViagemItem extends DominioItem
{
  private transient MarkerOptions _objMarkerOptions;

  @Override
  protected BitmapDescriptor getObjIcon()
  {
    return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
  }
}