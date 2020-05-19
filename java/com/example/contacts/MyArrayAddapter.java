package com.example.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyArrayAddapter  extends ArrayAdapter<String>
{
    ArrayList<String>nameList=new ArrayList<String>();
    ArrayList<String>numberList=new ArrayList<String>();
    MainActivity ctx;

    public MyArrayAddapter(MainActivity mainActivity, ArrayList<String> nameList, ArrayList<String> numberList) {

        super(mainActivity,R.layout.row,nameList);
        ctx=mainActivity;
        this.nameList=nameList;
        this.numberList=numberList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=ctx.getLayoutInflater();
       View newView= layoutInflater.inflate(R.layout.row,null,true);

       TextView name=newView.findViewById(R.id.names);
       TextView number=newView.findViewById(R.id.numbers);

       name.setText(this.nameList.get(position));
       number.setText(this.numberList.get(position));
       return newView;
    }
}
