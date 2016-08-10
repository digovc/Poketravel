package com.digosofter.poketravel.dominio;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class DominioItem extends DominioMain
{
  private LatLng _objLatLng;
  private transient Marker _objMarker;
  private transient MarkerOptions _objMarkerOptions;

  protected abstract BitmapDescriptor getObjIcon();

  public LatLng getObjLatLng()
  {
    return _objLatLng;
  }

  public Marker getObjMarker()
  {
    return _objMarker;
  }

  public MarkerOptions getObjMarkerOptions()
  {
    if (_objMarkerOptions != null)
    {
      return _objMarkerOptions;
    }

    if (this.getObjLatLng() == null)
    {
      return null;
    }

    _objMarkerOptions = new MarkerOptions();

    _objMarkerOptions.position(this.getObjLatLng());
    _objMarkerOptions.title(this.getStrNome());
    _objMarkerOptions.icon(this.getObjIcon());

    return _objMarkerOptions;
  }

  public void setObjLatLng(LatLng objLatLng)
  {
    _objLatLng = objLatLng;
  }

  public void setObjMarker(Marker objMarker)
  {
    _objMarker = objMarker;
  }

}