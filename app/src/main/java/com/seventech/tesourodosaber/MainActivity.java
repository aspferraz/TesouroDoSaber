package com.seventech.tesourodosaber;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.navigation.BackNavigable;


/**
 * Created by aspferraz on 11/11/2018.
 */

public class MainActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
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

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme_NoActionBar);
//        super.onCreate(savedInstanceState);
//    }
}
