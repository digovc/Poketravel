package com.digosofter.poketravel.componente;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.digosofter.digodroid.componente.painel.PainelMain;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class PainelMapa extends PainelMain implements OnMapReadyCallback
{
  private GoogleMap _objGoogleMap;
  private MapView _viwMap;

  public PainelMapa(final Context context)
  {
    super(context);
  }

  public PainelMapa(final Context context, final AttributeSet attrs)
  {
    super(context, attrs);
  }

  public PainelMapa(final Context context, final AttributeSet attrs, final int defStyleAttr)
  {
    super(context, attrs, defStyleAttr);
  }

  private GoogleMap getObjGoogleMap()
  {
    return _objGoogleMap;
  }

  private MapView getViwMap()
  {
    if (_viwMap != null)
    {
      return _viwMap;
    }

    _viwMap = new MapView(this.getContext());

    return _viwMap;
  }

  @Override
  public void inicializar()
  {
    super.inicializar();

    this.getViwMap().getMapAsync(this);
  }

  @Override
  public void montarLayout()
  {
    super.montarLayout();

    this.addView(this.getViwMap(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
  }

  @Override
  public void onMapReady(final GoogleMap objGoogleMap)
  {
    this.setObjGoogleMap(objGoogleMap);
  }

  private void setObjGoogleMap(GoogleMap objGoogleMap)
  {
    _objGoogleMap = objGoogleMap;
  }
}