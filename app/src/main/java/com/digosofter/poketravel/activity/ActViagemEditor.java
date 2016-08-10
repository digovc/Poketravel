package com.digosofter.poketravel.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.arquivo.ArqViagem;
import com.digosofter.poketravel.dominio.Viagem;
import com.digosofter.poketravel.dominio.ViagemItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class ActViagemEditor extends ActPoketravelMain
{
  private final String STR_MENU_ITEM_SALVAR_VIAGEM = "Salvar viagem";
  private Viagem _objViagem;
  private ViagemItem _objViagemItem;

  private void addViagemItem(final LatLng objLatLng)
  {
    if (objLatLng == null)
    {
      return;
    }

    this.setObjViagemItem(new ViagemItem());
    this.getObjViagemItem().setObjLatLng(objLatLng);

    this.getObjViagem().addObjViagemItem(this.getObjViagemItem());

    this.addViagemItem(this.getObjViagem(), this.getObjViagemItem());
  }

  private Viagem getObjViagem()
  {
    if (_objViagem != null)
    {
      return _objViagem;
    }

    _objViagem = new Viagem();

    return _objViagem;
  }

  private ViagemItem getObjViagemItem()
  {
    return _objViagemItem;
  }

  @Override
  protected void inicializar(final GoogleMap objGoogleMap)
  {
    super.inicializar(objGoogleMap);

    this.inicializarObjPolyline();
  }

  private void inicializarObjPolyline()
  {
    this.getObjViagem().inicializar(this.getObjGoogleMap());
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu mnu)
  {
    super.onCreateOptionsMenu(mnu);

    mnu.add(STR_MENU_ITEM_SALVAR_VIAGEM).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    return true;
  }

  @Override
  public void onMapClick(final LatLng objLatLng)
  {
    this.addViagemItem(objLatLng);
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
      case STR_MENU_ITEM_SALVAR_VIAGEM:
        return this.salvarViagem();
    }

    return false;
  }

  private boolean salvarViagem()
  {
    if (this.getObjViagem() == null)
    {
      return false;
    }

    if (this.getObjViagem().getArrObjViagemItem() == null)
    {
      return false;
    }

    if (this.getObjViagem().getArrObjViagemItem().isEmpty())
    {
      return false;
    }

    this.getObjViagem().setStrNome("Viagem de teste");

    ArqViagem arqViagem = new ArqViagem();

    arqViagem.setStrConteudo(AppPoketravel.getI().getObjGson().toJson(this.getObjViagem()));
    arqViagem.setStrNome(this.getObjViagem().getStrNome());

    arqViagem.salvar();

    return true;
  }

  private void setObjViagem(Viagem objViagem)
  {
    _objViagem = objViagem;
  }

  private void setObjViagemItem(ViagemItem objViagemItem)
  {
    _objViagemItem = objViagemItem;
  }
}