package com.seventech.tesourodosaber;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.navigation.BackNavigable;

public class FimJogoActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FimJogoActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return FimJogoFragment.newInstance();
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
