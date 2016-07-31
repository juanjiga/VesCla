package com.juanjiga.vescla;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;


public class CursorAdapter extends SimpleCursorAdapter {

    public CursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.colImp = cursor.getColumnIndexOrThrow(DataBaseAdapter.COL_USUARIO);
            holder.listTab = view.findViewById(R.id.Nombre_textView);
        }


    }


    static class ViewHolder {
        //almacena el index de la columna
        int colImp;
        //store th view
        View listTab;
    }


}
