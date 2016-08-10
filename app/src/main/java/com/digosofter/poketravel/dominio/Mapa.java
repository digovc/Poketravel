package com.digosofter.poketravel.dominio;

import java.util.ArrayList;

public class Mapa extends DominioMain
{
  private ArrayList<MapaItem> _arrObjMapaItem;

  public void addObjMapaItem(final MapaItem objMapaItem)
  {
    if (objMapaItem == null)
    {
      return;
    }

    if (objMapaItem.getObjLatLng() == null)
    {
      return;
    }

    if (objMapaItem.getEnmTipo().equals(MapaItem.EnmTipo.NONE))
    {
      return;
    }

    if (this.getArrObjMapaItem() == null)
    {
      this.setArrObjMapaItem(new ArrayList<MapaItem>());
    }

    this.getArrObjMapaItem().add(objMapaItem);
  }

  public ArrayList<MapaItem> getArrObjMapaItem()
  {
    return _arrObjMapaItem;
  }

  public void setArrObjMapaItem(ArrayList<MapaItem> arrObjMapaItem)
  {
    _arrObjMapaItem = arrObjMapaItem;
  }
}