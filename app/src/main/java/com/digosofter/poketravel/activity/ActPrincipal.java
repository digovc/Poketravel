package com.digosofter.poketravel.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.arquivo.ArqViagem;
import com.digosofter.poketravel.dominio.Viagem;
import com.digosofter.poketravel.dominio.ViagemItem;
import com.digosofter.poketravel.notification.ControlNotification;
import com.digosofter.poketravel.service.SrvViagem;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class ActPrincipal extends ActPoketravelMain
{
  private final String STR_MENU_ITEM_CARREGAR_VIAGEM = "Carregar viagem";
  private final String STR_MENU_ITEM_CONFIGURACAO = "Configuração";
  private final String STR_MENU_ITEM_CRIAR_MAPA = "Criar mapa";
  private final String STR_MENU_ITEM_CRIAR_VIAGEM = "Criar viagem";
  private final String STR_MENU_ITEM_INICIAR_VIAGEM = "Iniciar viagem";
  private final String STR_MENU_ITEM_PARAR_VIAGEM = "Parar viagem";
  private ControlNotification _objControlNotification;
  private Viagem _objViagem;

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

    LatLngBounds.Builder objLatLngBoundsBuilder = LatLngBounds.builder();

    for (ViagemItem objViagemItem : this.getObjViagem().getArrObjViagemItem())
    {
      this.addViagemItem(objLatLngBoundsBuilder, this.getObjViagem(), objViagemItem);
    }

    this.zoom(objLatLngBoundsBuilder);

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

  @Override
  protected void finalizar()
  {
    super.finalizar();

    AppPoketravel.getI().setBooPararViagem(true);

    this.getObjControlNotification().fechar();
  }

  private ControlNotification getObjControlNotification()
  {
    if (_objControlNotification != null)
    {
      return _objControlNotification;
    }

    _objControlNotification = new ControlNotification(this);

    return _objControlNotification;
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

    AppPoketravel.getI().setBooPararViagem(false);
    AppPoketravel.getI().setObjLatLng(new LatLng(this.getObjViagem().getArrObjViagemItem().get(0).getObjLatLng().latitude, this.getObjViagem().getArrObjViagemItem().get(0).getObjLatLng().longitude));
    AppPoketravel.getI().setObjViagem(this.getObjViagem());

    this.mostrarNotificacao();

    Intent itt = new Intent(this, SrvViagem.class);

    this.startService(itt);

    this.invalidateOptionsMenu();

    return true;
  }

  private void mostrarNotificacao()
  {
    this.getObjControlNotification().mostrarNotificacao();
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
  protected void onNewIntent(final Intent itt)
  {
    super.onNewIntent(itt);

    if (itt.getBooleanExtra(ControlNotification.STR_CONTROL_NOTIFICATION_COMANDO, false))
    {
      this.getObjControlNotification().processarOnClick();
      return;
    }
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