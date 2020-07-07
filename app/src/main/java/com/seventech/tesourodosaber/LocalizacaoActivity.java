package com.seventech.tesourodosaber;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.model.Localizacao;
import com.seventech.tesourodosaber.navigation.BackNavigable;

public class LocalizacaoActivity extends SingleFragmentActivity {

    public static final String EXTRA_LOCALIZACAO_BEAN = "com.seventech.tesourodosaber.localizacao_bean";

    public static Intent newIntent(Context packageContext, Localizacao localizacaoBean) {
        Intent intent = new Intent(packageContext, LocalizacaoActivity.class);
        intent.putExtra(EXTRA_LOCALIZACAO_BEAN, (Parcelable) localizacaoBean);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Localizacao localizacaoBean = (Localizacao) getIntent().getSerializableExtra(EXTRA_LOCALIZACAO_BEAN);
        return LocalizacaoFragment.newInstance(localizacaoBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof BackNavigable) {
            ((BackNavigable) fragment).onBackPressed();
        }
        else {
            super.onBackPressed();
        }
    }
}
