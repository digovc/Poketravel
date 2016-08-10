package com.digosofter.poketravel.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.SrvGpsMock;
import com.digosofter.poketravel.arquivo.ArqMapa;
import com.digosofter.poketravel.arquivo.ArqViagem;
import com.digosofter.poketravel.dominio.Mapa;
import com.digosofter.poketravel.dominio.MapaItem;
import com.digosofter.poketravel.dominio.Viagem;
import com.digosofter.poketravel.dominio.ViagemItem;

public class ActPrincipal extends ActPoketravelMain
{
  private final String STR_MENU_ITEM_CARREGAR_MAPA = "Carregar mapa";
  private final String STR_MENU_ITEM_CARREGAR_VIAGEM = "Carregar viagem";
  private final String STR_MENU_ITEM_CONFIGURACAO = "Configuração";
  private final String STR_MENU_ITEM_CRIAR_MAPA = "Criar mapa";
  private final String STR_MENU_ITEM_CRIAR_VIAGEM = "Criar viagem";
  private final String STR_MENU_ITEM_INICIAR_VIAGEM = "Iniciar viagem";
  private final String STR_MENU_ITEM_PARAR_VIAGEM = "Parar viagem";

  private Viagem _objViagem;

  private boolean carregarMapa()
  {
    ArqMapa arqMapa = new ArqMapa();

    arqMapa.setStrNome("Mapa de teste.pokemapa");

    Mapa objMapa = AppPoketravel.getI().getObjGson().fromJson(arqMapa.getStrConteudo(), Mapa.class);

    if (objMapa == null)
    {
      return false;
    }

    this.carregarMapa(objMapa);

    return true;
  }

  private void carregarMapa(final Mapa objMapa)
  {
    if (objMapa == null)
    {
      return;
    }

    if (objMapa.getArrObjMapaItem() == null)
    {
      return;
    }

    if (objMapa.getArrObjMapaItem().isEmpty())
    {
      return;
    }

    for (MapaItem objMapaItem : objMapa.getArrObjMapaItem())
    {
      this.addMapaItem(objMapaItem);
    }
  }

  private boolean carregarViagem()
  {
    ArqViagem arqViagem = new ArqViagem();

    arqViagem.setStrNome("Viagem de teste.pokeviagem");

    this.setObjViagem(AppPoketravel.getI().getObjGson().fromJson(arqViagem.getStrConteudo(), Viagem.class));

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

    if (this.getObjGoogleMap() == null)
    {
      return false;
    }

    this.getObjViagem().inicializar(this.getObjGoogleMap());

    for (ViagemItem objViagemItem : getObjViagem().getArrObjViagemItem())
    {
      this.addViagemItem(getObjViagem(), objViagemItem);
    }

    return true;
  }

  private boolean criarMapa()
  {
    this.startActivity(new Intent(this, ActMapaEditor.class));
    return true;
  }

  private boolean criarViagem()
  {
    this.startActivity(new Intent(this, ActViagemEditor.class));
    return true;
  }

  private Viagem getObjViagem()
  {
    return _objViagem;
  }

  @Override
  protected void inicializarApp()
  {
    super.inicializarApp();

    AppPoketravel.getI();
  }

  private boolean iniciarViagem()
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

    AppPoketravel.getI().setObjViagem(this.getObjViagem());
    AppPoketravel.getI().setObjLatLng(this.getObjViagem().getArrObjViagemItem().get(0).getObjLatLng());
    AppPoketravel.getI().setBooPararViagem(false);

    this.mostrarNotificacao();

    Intent itt = new Intent(this, SrvGpsMock.class);

    this.startService(itt);

    this.invalidateOptionsMenu();

    return true;
  }

  private void mostrarNotificacao()
  {

  }

  @Override
  public boolean onCreateOptionsMenu(final Menu mnu)
  {
    super.onCreateOptionsMenu(mnu);

    mnu.add(STR_MENU_ITEM_CRIAR_MAPA).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    mnu.add(STR_MENU_ITEM_CRIAR_VIAGEM).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    mnu.add(STR_MENU_ITEM_CARREGAR_MAPA).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    mnu.add(STR_MENU_ITEM_CARREGAR_VIAGEM).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    mnu.add(STR_MENU_ITEM_CONFIGURACAO).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    if (AppPoketravel.getI().getBooPararViagem())
    {
      mnu.add(STR_MENU_ITEM_INICIAR_VIAGEM).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    else
    {
      mnu.add(STR_MENU_ITEM_PARAR_VIAGEM).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    return true;
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

      case STR_MENU_ITEM_CARREGAR_MAPA:
        return this.carregarMapa();

      case STR_MENU_ITEM_CARREGAR_VIAGEM:
        return this.carregarViagem();

      case STR_MENU_ITEM_CONFIGURACAO:
        return false;

      case STR_MENU_ITEM_CRIAR_MAPA:
        return this.criarMapa();

      case STR_MENU_ITEM_CRIAR_VIAGEM:
        return this.criarViagem();

      case STR_MENU_ITEM_INICIAR_VIAGEM:
        return this.iniciarViagem();

      case STR_MENU_ITEM_PARAR_VIAGEM:
        return this.pararViagem();
    }

    return false;
  }

  private boolean pararViagem()
  {
    AppPoketravel.getI().setBooPararViagem(true);

    this.invalidateOptionsMenu();

    return true;
  }

  private void setObjViagem(Viagem objViagem)
  {
    _objViagem = objViagem;
  }
}