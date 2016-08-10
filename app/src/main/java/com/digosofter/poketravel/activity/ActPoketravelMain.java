package com.digosofter.poketravel.activity;

import android.widget.LinearLayout;

import com.digosofter.digodroid.activity.ActMapaMain;
import com.digosofter.poketravel.R;
import com.digosofter.poketravel.dominio.MapaItem;
import com.digosofter.poketravel.dominio.Viagem;
import com.digosofter.poketravel.dominio.ViagemItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public abstract class ActPoketravelMain extends ActMapaMain implements GoogleMap.OnMapClickListener
{
  private LinearLayout _pnlMapaContainer;

  protected void addMapaItem(final MapaItem objMapaItem)
  {
    if (this.getObjGoogleMap() == null)
    {
      return;
    }

    if (objMapaItem == null)
    {
      return;
    }

    if (objMapaItem.getObjLatLng() == null)
    {
      return;
    }

    objMapaItem.setObjMarker(this.getObjGoogleMap().addMarker(objMapaItem.getObjMarkerOptions()));
  }

  protected void addViagemItem(final Viagem objViagem, final ViagemItem objViagemItem)
  {
    if (objViagem == null)
    {
      return;
    }

    if (objViagemItem == null)
    {
      return;
    }

    if (objViagemItem.getObjLatLng() == null)
    {
      return;
    }

    if (this.getObjGoogleMap() == null)
    {
      return;
    }

    this.addViagemItemPolyline(objViagem, objViagemItem);
  }

  private void addViagemItemPolyline(final Viagem objViagem, final ViagemItem objViagemItem)
  {
    List<LatLng> lstObjLatLng = objViagem.getObjPolyline().getPoints();

    lstObjLatLng.add(objViagemItem.getObjLatLng());

    objViagem.getObjPolyline().setPoints(lstObjLatLng);
  }

  @Override
  protected int getIntLayoutId()
  {
    return R.layout.act_mapa;
  }

  @Override
  protected int getIntMapaContainerId()
  {
    return this.getPnlMapaContainer().getId();
  }

  protected LinearLayout getPnlMapaContainer()
  {
    if (_pnlMapaContainer != null)
    {
      return _pnlMapaContainer;
    }

    _pnlMapaContainer = this.getView(R.id.actMapa_pnlMapaContainer, LinearLayout.class);

    return _pnlMapaContainer;
  }

  @Override
  protected void inicializar()
  {
    super.inicializar();

    this.registerForContextMenu(this.getPnlMapaContainer());
  }

  @Override
  public void onMapClick(final LatLng objLatLng)
  {
  }

  @Override
  protected void setEventos(final GoogleMap objGoogleMap)
  {
    super.setEventos(objGoogleMap);

    objGoogleMap.setOnMapClickListener(this);
  }
}