package com.ibm.experimental.bank.eod.local;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ibm.experimental.bank.eod.local.impl.GlobalManager;
import com.ibm.experimental.bank.eod.local.impl.LocalManager;
import com.ibm.experimental.bank.eod.local.impl.Processor;

/**
 * Local Test <br>
 * Construct:<br>
 * - 1 Global Manager<br>
 * - 4 Local Managers
 * 
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class LocalTest {

	/**
	 * Processors count
	 */
	private static final int THREADS_COUNT = 1_000;
	/**
	 * Number of messages for test
	 */
	public static final int MAX_MESSAGES = 12_000;
	/**
	 * Trigger EOD on Message index
	 */
	public static final int EOD_INDEX = 10_000;

	private GlobalManager global;
	private LocalManager[] locals;

	@Before
	public void setUp() throws Exception {
		this.global = new GlobalManager();
		this.locals = new LocalManager[] {new LocalManager(), new LocalManager(), new LocalManager(), new LocalManager()};
		for (int i = 0; i < locals.length; i++) {
			global.addListener(locals[i]);
			locals[i].addListener(global);
		}
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("TEST END");
	}

	@Test
	public void test() throws InterruptedException {
		Thread[] t = new Thread[THREADS_COUNT];
		for (int i = 0; i < THREADS_COUNT; i++) {
			t[i] = new Thread(new Processor(global, locals[i % 4]));
			t[i].start();
			System.out.println("Processor started " + i);
		}
		
		for (int i = 0; i < 1000; i++) {
			t[i].join();
		}
	}

}
