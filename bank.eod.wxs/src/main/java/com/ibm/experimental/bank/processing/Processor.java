package com.ibm.experimental.bank.processing;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.experimental.bank.eod.IMessage;
import com.ibm.experimental.bank.eod.def.AbstractProcessor;
import com.ibm.experimental.bank.eod.wxs.LocalManager;
import com.ibm.experimental.bank.eod.wxs.WxsManager;
import com.ibm.experimental.bank.rest.Message;

/**
 * Processor: Change for your purpose
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class Processor extends AbstractProcessor implements Runnable {
	private static final Log log = LogFactory.getLog(Processor.class);
	private boolean active = true;

	private static Collection<Processor> processors = Collections.synchronizedCollection(new HashSet<Processor>());
	
	public Processor() {
		super(WxsManager.getInstance(), LocalManager.getInstance());
	}

	@Override
	public void doPayment(IMessage message) {
		if (!active)
			return;
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			log.error("Processing interrupted", e);
		}
		log.info("Message % Processed".replaceAll("%", message.getId()));

	}

	@Override
	public void beforeEod(IMessage message) {
		if (!active)
			return;
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			log.error("EOD preparation phase interrupted", e);
		}
		log.debug("Before EOD completed");
	}

	@Override
	public void doEod(IMessage message) {
		if (!active)
			return;
		log.info("EOD started");
		try {
			Thread.sleep(500);
			log.info("EOD completed");
//			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("EOD phase interrupted", e);
		}
	}
	
	public void stop() {
		active = false;
	}

	@Override
	public void run() {
		processors.add(this);
		try {
			for (int i = 0; i < 1000; i++) {
				IMessage message = new Message();
				if (message.isEOD())
					processEOD(message);
				else
					processPayment(message);
			}
			active = false;
		} finally {
			processors.remove(this);
		}
	}

	public static Processor next() {
		return processors.iterator().next();
	}

	public static void stopAll() {
		for (Processor processor : processors) {
			processor.stop();
		}
	}

}
