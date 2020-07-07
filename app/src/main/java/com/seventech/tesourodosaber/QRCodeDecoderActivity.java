package com.seventech.tesourodosaber;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.seventech.tesourodosaber.navigation.BackNavigable;

public class QRCodeDecoderActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, QRCodeDecoderActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return QRCodeDecoderFragment.newInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
