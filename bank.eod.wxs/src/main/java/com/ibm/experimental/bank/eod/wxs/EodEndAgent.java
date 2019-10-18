package com.ibm.experimental.bank.eod.wxs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

/**
 * EOD End Agent, to resume paused processing
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class EodEndAgent extends AbstractEodAgent {

	private static final long serialVersionUID = -6334126015053299306L;
	private static final Log log = LogFactory.getLog(EodEndAgent.class);
	
	@Override
	public Object reduce(Session arg0, ObjectMap arg1) {
		log.info("------ EOD resume ------");
		LocalManager local = LocalManager.getInstance();
		local.eodEnd();
		return 1;
	}

}
