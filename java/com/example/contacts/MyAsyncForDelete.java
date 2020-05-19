package com.example.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

public class MyAsyncForDelete extends AsyncTask<String,Integer,String> {

    Context ct;
    ProgressDialog progressDialog;
    MyAsyncForDelete(Context ctx)
    {
        ct=ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(ct);
        progressDialog.setTitle("Deleting All");
        progressDialog.setMessage("Please Wait");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if(MainActivity.nameList.size()>0)
        progressDialog.show();
        else Toast.makeText(ct,"Phonebook is empty",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {

        if(MainActivity.nameList.size()>0 && MainActivity.nameList.size()<50)
        {
            for(int i=1;i<=100;i+=10)
            {
                try {
                    Thread.sleep(10);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Integer val=values[0];
        progressDialog.setProgress(val);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(MainActivity.nameList.size()>0) {
            MainActivity.myContactsDataBase.deleteAll();
            MainActivity.myContactsDataBase.getData();
            MyArrayAddapter myArrayAddapter = new MyArrayAddapter((MainActivity) ct, MainActivity.nameList, MainActivity.numberList);
            MainActivity.listView.setAdapter(myArrayAddapter);
            progressDialog.dismiss();
            Toast.makeText(ct, "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
