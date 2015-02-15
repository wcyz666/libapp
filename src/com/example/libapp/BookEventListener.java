package com.example.libapp;

import java.util.EventListener;

import android.provider.ContactsContract.CommonDataKinds.Event;

public interface BookEventListener extends EventListener{
	public void onBookOptOver(Event bookEvent);
}
