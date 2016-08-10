package com.digosofter.poketravel.arquivo;

import android.os.Environment;

import com.digosofter.digojava.Utils;
import com.digosofter.digojava.arquivo.ArquivoTxt;

public abstract class ArquivoPoketravelMain extends ArquivoTxt
{
  public ArquivoPoketravelMain()
  {
    this.iniciar();
  }

  protected abstract String getStrExtencao();

  private void inicializar()
  {
    this.setDir(Environment.getExternalStorageDirectory().getPath().concat("/Pokétravel"));
  }

  private void iniciar()
  {
    this.inicializar();
  }

  @Override
  public void salvar()
  {
    if (Utils.getBooStrVazia(this.getStrNome()))
    {
      return;
    }

    this.setStrNome(this.getStrNome().concat(".").concat(this.getStrExtencao()));

    super.salvar();
  }
}