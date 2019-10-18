package com.ibm.experimental.bank.eod.local.impl;

import com.ibm.experimental.bank.eod.IGlobalManager;
import com.ibm.experimental.bank.eod.ILocalManager;
import com.ibm.experimental.bank.eod.IMessage;
import com.ibm.experimental.bank.eod.def.AbstractProcessor;
import com.ibm.experimental.bank.eod.local.LocalTest;

/**
 * Local Test: Processor
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class Processor extends AbstractProcessor implements Runnable {


	public Processor(IGlobalManager global, ILocalManager local) {
		super(global, local);
	}

	@Override
	public void doPayment(IMessage message) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message % Processed".replaceAll("%", message.getId()));
	}

	@Override
	public void beforeEod(IMessage message) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Before EOD completed");
	}

	@Override
	public void doEod(IMessage message) {
		System.out.println("EOD started");
		try {
			Thread.sleep(1000);
			System.out.println("EOD completed");
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while (Message.index.get() < LocalTest.MAX_MESSAGES) {
			IMessage message = new Message();
			if (message.isEOD())
				processEOD(message);
			else
				processPayment(message);
		}
	}
}
