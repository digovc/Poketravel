package digosofter.com.poketravel.activity;

import com.digosofter.digodroid.activity.ActMain;

import digosofter.com.poketravel.R;

public abstract class ActPoketravelMain extends ActMain
{
  @Override
  protected void montarLayout()
  {
    super.montarLayout();

    this.addFragmento(this.getIntMapaContainer(), );
  }

  protected abstract void getIntMapaContainer();
}