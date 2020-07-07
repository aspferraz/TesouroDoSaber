package com.seventech.tesourodosaber;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.model.Enigma;
import com.seventech.tesourodosaber.navigation.BackNavigable;

public class EnigmaActivity extends SingleFragmentActivity {

     public static final String EXTRA_ENIGMA_BEAN = "com.seventech.tesourodosaber.enigma_bean";


    public static Intent newIntent(Context packageContext, Enigma enigmaBean) {
        Intent intent = new Intent(packageContext, EnigmaActivity.class);
        intent.putExtra(EXTRA_ENIGMA_BEAN, (Parcelable) enigmaBean);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Enigma enigmaBean = (Enigma) getIntent().getSerializableExtra(EXTRA_ENIGMA_BEAN);
        return EnigmaFragment.newInstance(enigmaBean);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        fragment.onActivityResult(requestCode, resultCode, data);
//    }

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
