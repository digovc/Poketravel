package com.digosofter.poketravel.dominio;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Viagem extends DominioMain
{
  private ArrayList<ViagemItem> _arrObjViagemItem;
  private transient Polyline _objPolyline;
  private transient PolylineOptions _objPolylineOptions;

  public void addObjViagemItem(final ViagemItem objViagemItem)
  {
    if (objViagemItem == null)
    {
      return;
    }

    if (this.getArrObjViagemItem() == null)
    {
      this.setArrObjViagemItem(new ArrayList<ViagemItem>());
    }

    this.getArrObjViagemItem().add(objViagemItem);
  }

  public ArrayList<ViagemItem> getArrObjViagemItem()
  {
    return _arrObjViagemItem;
  }

  public Polyline getObjPolyline()
  {
    return _objPolyline;
  }

  private PolylineOptions getObjPolylineOptions()
  {
    if (_objPolylineOptions != null)
    {
      return _objPolylineOptions;
    }

    _objPolylineOptions = new PolylineOptions();

    _objPolylineOptions.color(Color.BLUE);
    _objPolylineOptions.width(25);

    return _objPolylineOptions;
  }

  public void inicializar(final GoogleMap objGoogleMap)
  {
    if (objGoogleMap == null)
    {
      return;
    }

    this.setObjPolyline(objGoogleMap.addPolyline(this.getObjPolylineOptions()));
  }

  public void setArrObjViagemItem(ArrayList<ViagemItem> arrObjViagemItem)
  {
    _arrObjViagemItem = arrObjViagemItem;
  }

  public void setObjPolyline(Polyline objPolyline)
  {
    _objPolyline = objPolyline;
  }
}