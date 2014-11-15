package me.kaaninan.been;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.kaaninan.been.adapter.KayitAdapter;
import me.kaaninan.been.constructor.KayitConstructor;
import me.kaaninan.been.db.DatabaseManager;
import me.kaaninan.been.location.KordinatService;


public class Kayitlar extends Fragment {

    private DatabaseManager manager;

    private ViewGroup root;

    private ListView list;
    private KayitAdapter adapter;
    private ArrayList<KayitConstructor> array;

    private static final int CAMERA_REQUEST = 1;

    private ImageView iw1;
    private ImageView iw2;

    private ImageButton ib1;
    private ImageButton ib2;

    private ImageButton cameraButton;
    private ImageButton cameraButton2;

    private ImageButton gpsButton;

    private RelativeLayout layoutUst;
    private RelativeLayout layoutAlt;

    private LinearLayout forPadding;

    private EditText editWhats;
    private Button sendButton;

    public final static int KONUM_SECENEK = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        root = (ViewGroup) inflater.inflate(R.layout.kayitlar, container, false);

        list = (ListView) root.findViewById(R.id.listView);

        // Listeyi doldurma
        manager = new DatabaseManager(getActivity());
        array = manager.getKayitlar();

        adapter = new KayitAdapter(getActivity(), R.layout.list_kayitlar, array);
        list.setAdapter(adapter);


        // View Tanımalamaları

        iw1 = (ImageView) root.findViewById(R.id.imageView);
        iw2 = (ImageView) root.findViewById(R.id.imageView2);

        ib1 = (ImageButton) root.findViewById(R.id.imageButton);
        ib2 = (ImageButton) root.findViewById(R.id.imageButton2);

        layoutAlt = (RelativeLayout) root.findViewById(R.id.layoutAlt2);
        layoutUst = (RelativeLayout) root.findViewById(R.id.layoutUst);

        cameraButton = (ImageButton) root.findViewById(R.id.imageButtonKamera);
        cameraButton2 = (ImageButton) root.findViewById(R.id.imageButtonKamera2);

        gpsButton = (ImageButton) root.findViewById(R.id.imageButtonKonum);

        forPadding = (LinearLayout) root.findViewById(R.id.forPadding);

        editWhats = (EditText) root.findViewById(R.id.editWhats);
        sendButton = (Button) root.findViewById(R.id.buttonSend);





        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String message = editWhats.getText().toString();

                manager.ekleKayit(message, null);
                editWhats.setText("");
                guncelle();
                list.setSelection(adapter.getCount() - 1);
            }
        });





        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int kayit_id = (Integer) view.getTag();
                manager.silKayit(kayit_id);
                Toast.makeText(getActivity(), "Text => " + kayit_id, Toast.LENGTH_LONG).show();
                guncelle();
            }
        });





        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        cameraButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });



        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().showDialog(KONUM_SECENEK);
            }
        });




        // Resimleri kaldırma

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDuzenle("bir", "kaldir");
                isAllClear();
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDuzenle("iki", "kaldir");
                isAllClear();
            }
        });

        return root;
    }

    private void guncelle() {
        adapter = new KayitAdapter(getActivity(), R.layout.list_kayitlar, manager.getKayitlar());
        list.setAdapter(adapter);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    if (photo != null){

                        layoutScale("buyut");

                        if (iw1.getVisibility() == View.GONE){
                            iw1.setImageBitmap(photo);
                            layoutDuzenle("bir", "ekle");
                        }else {
                            iw2.setImageBitmap(photo);
                            layoutDuzenle("iki", "ekle");
                        }

                    }


                }
        }

    }


    // Layout Boyut İşlemleri

    private void layoutScale(String choose){

        int height = 0;

        if (choose == "buyut"){
            height = getResources().getDimensionPixelOffset(R.dimen.layoutAltHeightAfter);
        }else if (choose == "kucult"){
            height = getResources().getDimensionPixelOffset(R.dimen.layoutAltHeight);
        }

        int paddingTop = (int) getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_default_height);

        layoutUst.setPadding(0, paddingTop, 0, height);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutAlt.setLayoutParams(params);
    }

    private void layoutDuzenle(String hangisi, String ekleKaldir){

        if(hangisi == "bir"){

            if(ekleKaldir == "ekle"){

                if(iw2.getVisibility() == View.GONE){
                    cameraButton2.setVisibility(View.VISIBLE);
                }

                iw1.setVisibility(View.VISIBLE);
                ib1.setVisibility(View.VISIBLE);
                forPadding.setVisibility(View.VISIBLE);
            }

            else if(ekleKaldir == "kaldir"){

                // İki resimde çekilmişse
                if(iw2.getVisibility() == View.VISIBLE){

                    // iw2'den iw1'e aktarma

                    Bitmap bitmap = ((BitmapDrawable)iw2.getDrawable()).getBitmap();

                    iw1.setImageBitmap(bitmap);
                    iw2.setVisibility(View.GONE);
                    ib2.setVisibility(View.GONE);
                    cameraButton2.setVisibility(View.VISIBLE);

                }else{
                    cameraButton2.setVisibility(View.GONE);
                    iw1.setVisibility(View.GONE);
                    iw1.setImageBitmap(null);
                    ib1.setVisibility(View.GONE);
                    forPadding.setVisibility(View.GONE);
                }
            }

        }else if(hangisi == "iki"){

            if(ekleKaldir == "ekle"){

                cameraButton2.setVisibility(View.GONE);
                iw2.setVisibility(View.VISIBLE);
                ib2.setVisibility(View.VISIBLE);

            }
            else if(ekleKaldir == "kaldir"){

                cameraButton2.setVisibility(View.VISIBLE);
                iw2.setVisibility(View.GONE);
                iw2.setImageBitmap(null);
                ib2.setVisibility(View.GONE);

            }

        }

    }

    private void isAllClear(){
        // Butun resimler silindiğinde boyut küçülür
        if (iw1.getVisibility() == View.GONE){
            if (iw2.getVisibility() == View.GONE){
                layoutScale("kucult");
            }
        }
    }






    // DIALOG


    public Dialog konumSecenekDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Konum Seçenekleri");
        builder.setMessage("Lütfen konumun nasıl bulunacağını seçiniz.");

        builder.setNeutralButton("Şu Anki Konumum", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getActivity(), KordinatService.class);
                getActivity().startService(intent);

            }
        });


        builder.setPositiveButton("Konumu Elle Gireceğim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Konmu", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();

    }



    // Dışardan gelen View Set'ler

    public void setViewKonum(String konum){
        TextView textKonum = (TextView) root.findViewById(R.id.textViewKonum);
        textKonum.setText(konum);
    }



}
