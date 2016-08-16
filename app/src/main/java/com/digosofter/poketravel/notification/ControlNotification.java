package com.digosofter.poketravel.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.digosofter.digojava.Objeto;
import com.digosofter.poketravel.AppPoketravel;
import com.digosofter.poketravel.activity.ActPrincipal;

public class ControlNotification extends Objeto
{
  public static final String STR_CONTROL_NOTIFICATION_COMANDO = "control_notification_comando";
  private static final String STR_TEXT_CONTINUAR = "Continuar a viagem";
  private static final String STR_TEXT_PAUSAR = "Pausar a viagem";

  private ActPrincipal _actPrincipal;
  private int _intNotificacaoId;
  private PendingIntent _ittPending;
  private Notification.Builder _objBuilder;
  private NotificationManager _objNotificationManager;

  public ControlNotification(ActPrincipal actPrincipal)
  {
    this.setActPrincipal(actPrincipal);
  }

  public void fechar()
  {
    this.getObjNotificationManager().cancelAll();
  }

  private ActPrincipal getActPrincipal()
  {
    return _actPrincipal;
  }

  private int getIntNotificacaoId()
  {
    return _intNotificacaoId;
  }

  private PendingIntent getIttPending()
  {
    if (_ittPending != null)
    {
      return _ittPending;
    }

    Intent itt = new Intent(this.getActPrincipal(), ActPrincipal.class);

    itt.putExtra(STR_CONTROL_NOTIFICATION_COMANDO, true);

    _ittPending = PendingIntent.getActivity(this.getActPrincipal(), 0, itt, PendingIntent.FLAG_UPDATE_CURRENT);

    return _ittPending;
  }

  private Notification.Builder getObjBuilder()
  {
    if (_objBuilder != null)
    {
      return _objBuilder;
    }

    _objBuilder = new Notification.Builder(this.getActPrincipal());

    _objBuilder.setContentIntent(this.getIttPending());
    _objBuilder.setContentText(STR_TEXT_PAUSAR);
    _objBuilder.setContentTitle("Pokétravel");
    _objBuilder.setSmallIcon(android.support.design.R.drawable.notification_template_icon_bg);

    return _objBuilder;
  }

  private NotificationManager getObjNotificationManager()
  {
    if (_objNotificationManager != null)
    {
      return _objNotificationManager;
    }

    _objNotificationManager = (NotificationManager) this.getActPrincipal().getSystemService(Context.NOTIFICATION_SERVICE);

    return _objNotificationManager;
  }

  public void mostrarNotificacao()
  {
    this.setIntNotificacaoId(this.getIntNotificacaoId() + 1);

    Notification objNotification = this.getObjBuilder().build();

    objNotification.flags = Notification.FLAG_NO_CLEAR;

    this.getObjNotificationManager().notify(this.getIntNotificacaoId(), objNotification);
  }

  public void processarOnClick()
  {
    if (AppPoketravel.getI().getBooPararViagem())
    {
      return;
    }

    AppPoketravel.getI().setBooPausarViagem(!AppPoketravel.getI().getBooPausarViagem());

    this.getObjNotificationManager().cancel(this.getIntNotificacaoId());

    this.getObjBuilder().setContentText(AppPoketravel.getI().getBooPausarViagem() ? STR_TEXT_CONTINUAR : STR_TEXT_PAUSAR);

    this.mostrarNotificacao();
  }

  private void setActPrincipal(ActPrincipal actPrincipal)
  {
    _actPrincipal = actPrincipal;
  }

  private void setIntNotificacaoId(int intNotificacaoId)
  {
    _intNotificacaoId = intNotificacaoId;
  }
}