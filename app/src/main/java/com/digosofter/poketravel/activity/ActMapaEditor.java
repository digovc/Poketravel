package com.digosofter.poketravel.activity;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.arquivo.ArqMapa;
import com.digosofter.poketravel.dominio.Mapa;
import com.digosofter.poketravel.dominio.MapaItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class ActMapaEditor extends ActPoketravelMain implements MenuItem.OnMenuItemClickListener
{
  private final String STR_CONTEXTO_MENU_ADD_GINASIO = "Adicionar Ginásio";
  private final String STR_CONTEXTO_MENU_ADD_POKESHOP = "Adicionar Pokéshop";
  private final String STR_MENU_ITEM_SALVAR_MAPA = "Salvar mapa";

  private Mapa _objMapa;
  private MapaItem _objMapaItem;

  private void addMapaItem(final LatLng objLatLng)
  {
    if (objLatLng == null)
    {
      return;
    }

    this.setObjMapaItem(new MapaItem());
    this.getObjMapaItem().setObjLatLng(objLatLng);

    this.getPnlMapaContainer().showContextMenu();
  }

  private boolean addMapaItem(final MapaItem.EnmTipo enmTipo)
  {
    if (this.getObjMapaItem() == null)
    {
      return false;
    }

    if (this.getObjMapaItem().getObjLatLng() == null)
    {
      return false;
    }

    this.getObjMapaItem().setEnmTipo(enmTipo);

    this.getObjMapa().addObjMapaItem(this.getObjMapaItem());

    this.addMapaItem(this.getObjMapaItem());

    return true;
  }

  private boolean addMapaItemGinazio()
  {
    return this.addMapaItem(MapaItem.EnmTipo.GINASIO);
  }

  private boolean addMapaItemPokeshop()
  {
    return this.addMapaItem(MapaItem.EnmTipo.POKESHOP);
  }

  private Mapa getObjMapa()
  {
    if (_objMapa != null)
    {
      return _objMapa;
    }

    _objMapa = new Mapa();

    return _objMapa;
  }

  private MapaItem getObjMapaItem()
  {
    return _objMapaItem;
  }

  @Override
  public void onCreateContextMenu(final ContextMenu mnu, final View viw, final ContextMenu.ContextMenuInfo objContextMenuInfo)
  {
    super.onCreateContextMenu(mnu, viw, objContextMenuInfo);

    if (viw.equals(this.getPnlMapaContainer()))
    {
      this.onCreateContextMenuMapa(mnu);
      return;
    }
  }

  private void onCreateContextMenuMapa(final ContextMenu mnu)
  {
    if (this.getObjMapaItem() == null)
    {
      mnu.close();
      return;
    }

    mnu.add(STR_CONTEXTO_MENU_ADD_GINASIO).setOnMenuItemClickListener(this);
    mnu.add(STR_CONTEXTO_MENU_ADD_POKESHOP).setOnMenuItemClickListener(this);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu mnu)
  {
    super.onCreateOptionsMenu(mnu);

    mnu.add(STR_MENU_ITEM_SALVAR_MAPA).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    return true;
  }

  @Override
  public void onMapClick(final LatLng objLatLng)
  {
    this.addMapaItem(objLatLng);
  }

  @Override
  public boolean onMenuItemClick(final MenuItem mnuItem)
  {
    switch (mnuItem.getTitle().toString())
    {
      case STR_CONTEXTO_MENU_ADD_GINASIO:
        return this.addMapaItemGinazio();

      case STR_CONTEXTO_MENU_ADD_POKESHOP:
        return this.addMapaItemPokeshop();
    }

    return false;
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem mni)
  {
    if (super.onOptionsItemSelected(mni))
    {
      return true;
    }

    switch (mni.getTitle().toString())
    {
      case STR_MENU_ITEM_SALVAR_MAPA:
        return this.salvarMapa();
    }

    return false;
  }

  private boolean salvarMapa()
  {
    if (this.getObjMapa().getArrObjMapaItem() == null)
    {
      return false;
    }

    if (this.getObjMapa().getArrObjMapaItem().isEmpty())
    {
      return false;
    }

    this.getObjMapa().setStrNome("Mapa de teste");

    ArqMapa arqMapa = new ArqMapa();

    arqMapa.setStrConteudo(AppPoketravel.getI().getObjGson().toJson(this.getObjMapa()));
    arqMapa.setStrNome(this.getObjMapa().getStrNome());

    arqMapa.salvar();

    return true;
  }

  @Override
  protected void setEventos(final GoogleMap objGoogleMap)
  {
    super.setEventos(objGoogleMap);

    objGoogleMap.setOnMapClickListener(this);
  }

  private void setObjMapa(Mapa objMapa)
  {
    _objMapa = objMapa;
  }

  private void setObjMapaItem(MapaItem objMapaItem)
  {
    _objMapaItem = objMapaItem;
  }
}