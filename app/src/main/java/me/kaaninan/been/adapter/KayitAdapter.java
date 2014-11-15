package me.kaaninan.been.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.kaaninan.been.R;
import me.kaaninan.been.constructor.KayitConstructor;


public class KayitAdapter extends BaseAdapter {

    private String LOG = "KayitAdapter";

    private Context context;
    private List<KayitConstructor> list;
    private int layoutResourceId;

    public KayitAdapter(Context context, int layoutResourceId, List<KayitConstructor> list) {
        super();
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.list = list;
    }

    public int getCount() {
        if(list.size() == 0)
            return 1;
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResourceId, null);
        }

        TextView text = (TextView) view.findViewById(R.id.text);
        TextView tarih = (TextView) view.findViewById(R.id.textTarih);

        if(list.size() <= 0){
            text.setText("Burası Boş");

        }else{
            KayitConstructor kayit = list.get(position);
            text.setText(kayit.text);
            tarih.setText(kayit.tarih);
            view.setTag(kayit.id);
        }
        return view;

    }

}
