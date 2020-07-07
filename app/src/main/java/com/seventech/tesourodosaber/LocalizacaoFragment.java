package com.seventech.tesourodosaber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;
import com.seventech.tesourodosaber.database.repository.EnigmasRepository;
import com.seventech.tesourodosaber.model.Enigma;
import com.seventech.tesourodosaber.model.Localizacao;
import com.seventech.tesourodosaber.navigation.BackNavigable;
import com.seventech.tesourodosaber.utils.view.Messages;

import java.io.IOException;
import java.io.InputStream;

public class LocalizacaoFragment extends Fragment implements BackNavigable {

    private static final int REQUEST_QRCODE_READER = 0;

//    private ImageView mMapaImageView;
    private PhotoView mMapaPhotoView;
    private ImageButton mLerQRCodeButton;

    private Enigma mEnigma;
    EnigmasRepository mEnigmasRepository;

    private LocalizacaoActivity mLocalizacaoActivity;
    private Localizacao mLocalizacao;

    private static final String ARG_LOCALIZACAO_BEAN = "localizacao_bean";

    public static LocalizacaoFragment newInstance(Localizacao localizacao) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCALIZACAO_BEAN, localizacao);
        LocalizacaoFragment fragment = new LocalizacaoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mLocalizacao = (Localizacao) getArguments().getSerializable(ARG_LOCALIZACAO_BEAN);

        mEnigmasRepository = new EnigmasRepository(mLocalizacaoActivity.getApplication());

        mEnigma = mEnigmasRepository.getEnigmaById(mLocalizacao.getIdEnigma());

        App.getSessionManager().getCurrentSession().setIdUltimoEnigma(mLocalizacao.getIdEnigma());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_localizacao, container, false);

        mMapaPhotoView = view.findViewById(R.id.localizacao_mapa_photoview);
        mMapaPhotoView.setImageBitmap(getBitmapFromAsset(mLocalizacaoActivity.getApplicationContext(), mLocalizacao.getCaminhoArquivoMapa()));

        mLerQRCodeButton = view.findViewById(R.id.ler_qrcode_button);
        mLerQRCodeButton.setOnClickListener(new LerQRCodeImageButtonListener());

        return view;
    }

    class LerQRCodeImageButtonListener implements ImageButton.OnClickListener {

        @Override
        public void onClick(View v) {

            startActivityForResult(QRCodeDecoderActivity.newIntent(mLocalizacaoActivity), REQUEST_QRCODE_READER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_QRCODE_READER:

                if (resultCode == Activity.RESULT_OK) {
                    String qrCodeDecoded = data.getStringExtra(QRCodeDecoderFragment.EXTRA_QRCODE_DECODED_RESULT);
                    if (qrCodeDecoded.equals(mLocalizacao.getQrCode())) {
                        startActivity(EnigmaActivity.newIntent(mLocalizacaoActivity, mEnigma));
                    }
                    else {
                        Messages.with(getContext()).showInfoMessage("Local errado","Desculpe, o QRCode lido não corresponde ao local apontado no mapa. \n Encontre o local apontado no mapa e tente novamente.");
                    }
                }

                break;
        }
    }

    private Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    @Override
    public void onBackPressed() {


    }

    // gambiarra p/ contornar bug do android
    @Override
    public void onAttach(Activity activity) {
        mLocalizacaoActivity = (LocalizacaoActivity) activity;
        super.onAttach(activity);
    }
}
