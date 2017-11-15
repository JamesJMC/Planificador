package com.example.james.planificador.LogicaDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by James on 07/09/2017.
 */

public class EventosIntentService
{

    public static void UpdateData(Context context, String nombreEv)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
// New value for one column
        ContentValues values = new ContentValues();
        values.put(EventoContract.FeedEntry.NAME_EVENT, nombreEv);
// Which row to update, based on the title
        String selection = EventoContract.FeedEntry.NAME_EVENT + " LIKE ?";
        String[] selectionArgs = { nombreEv };
        int count = db.update(
                EventoContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
