package com.juanjiga.vescla;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;


public class CursorAdapter extends SimpleCursorAdapter {

    public CursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);
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
            holder.colImp = cursor.getColumnIndexOrThrow(DataBaseControl._id);
            //holder.listTabId = view.findViewById(R.id.Id_textView);
            holder.listTabNombre = view.findViewById(R.id.Nombre_textView);
            holder.listTabUsuario = view.findViewById(R.id.Usuario_textView);
            holder.listTabPassword = view.findViewById(R.id.Password_textView);
            view.setTag(holder);
        }
    }

    static class ViewHolder {
        //almacena el index de la columna
        int colImp;
        //store the view
        View listTabId, listTabNombre, listTabUsuario, listTabPassword;
    }


}
