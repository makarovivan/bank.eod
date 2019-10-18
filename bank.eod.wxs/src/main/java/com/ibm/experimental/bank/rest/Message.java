package com.ibm.experimental.bank.rest;

import java.util.concurrent.atomic.AtomicInteger;

import com.ibm.experimental.bank.eod.IMessage;

public class Message implements IMessage {
	public static AtomicInteger index = new AtomicInteger();
	private static int nextEod;
	private int id;

	public Message() {
		this.id = index.incrementAndGet();
	}
	
	@Override
	public boolean isEOD() {
		return id == nextEod;
	}

	@Override
	public String getId() {
		return "index " + id;
	}
	
	public static void setNextEod(int nextEod) {
		Message.nextEod = index.get() + nextEod;
	}
}
