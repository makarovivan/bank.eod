package com.ibm.experimental.bank.eod.wxs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.experimental.bank.eod.IProcessingListener;
import com.ibm.experimental.bank.eod.def.TimeUtils;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

/**
 * EOD Agent to put processing on Pause
 * 
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class EodStartAgent extends AbstractEodAgent implements IProcessingListener {

	private static final long serialVersionUID = 5874765328461418882L;
	private static final Log log = LogFactory.getLog(EodStartAgent.class);

	@Override
	public Object reduce(Session session, ObjectMap map) {
		log.info("------ EOD pause start ------");
		LocalManager local = LocalManager.getInstance();
		local.addListener(this);

		try {
			synchronized (this) {
				local.eodStart();
				try {
					long now = System.currentTimeMillis();
					while (local.count() > 0 && TimeUtils.waitCheck(now))
						wait(TimeUtils.waitTime(now));
				} catch (InterruptedException e) {
					log.error("EOD phase processing interrupted", e);
				}
			}
			log.info("------ EOD paused ------");
			return LocalManager.getInstance().count();
		} finally {
			local.removeListener(this);
		}
	}

	@Override
	public synchronized void activate() {
		log.warn("Processing activated during EOD phase. Check for consistency");
	}

	@Override
	public synchronized void deactivate() {
		notifyAll();
	}

}
