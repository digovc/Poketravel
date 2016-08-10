package digosofter.com.poketravel.activity;

import android.location.Location;

import com.digosofter.digodroid.activity.ActMapaMain;

import digosofter.com.poketravel.R;

/**
 * Created by Rodrigo on 08/08/2016.
 */
public class ActPrincipal extends ActMapaMain
{
  @Override
  protected int getIntLayoutId()
  {
    return R.layout.act_principal;
  }

  @Override
  protected int getIntMapContainerId()
  {
    return 0;
  }

  @Override
  public void onLocationChanged(final Location location)
  {

  }
}
