package com.ibm.experimental.bank.eod.local.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.ibm.experimental.bank.eod.IMessage;
import com.ibm.experimental.bank.eod.local.LocalTest;

/**
 * Local Test: Message implementation
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class Message implements IMessage {
	public static AtomicInteger index = new AtomicInteger();
	private int id;

	public Message() {
		id = index.incrementAndGet();
	}
	
	@Override
	public boolean isEOD() {
		return id % LocalTest.EOD_INDEX == 0;
	}

	@Override
	public String getId() {
		return "index " + id;
	}
}
