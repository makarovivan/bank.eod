package com.ibm.experimental.bank.eod.wxs;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.experimental.bank.eod.def.AbstractGlobalManager;
import com.ibm.websphere.objectgrid.ClientClusterContext;
import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridManagerFactory;
import com.ibm.websphere.objectgrid.ObjectGridRuntimeException;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.datagrid.AgentManager;
import com.ibm.websphere.objectgrid.datagrid.ReduceGridAgent;

/**
 * Global Manager implementation for WXS
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class WxsManager extends AbstractGlobalManager {

	private static final Log log = LogFactory.getLog(WxsManager.class);

	private static WxsManager instance;

	private ObjectGrid grid;
	private static final String GRID_NAME = "EOD";
	private static final String MAP_NAME = "sync";

	@Override
	public void eodReceived() {
		try {
			log.info("EOD message received");
			Session sess = getSession();
			ObjectMap map = sess.getMap(MAP_NAME);

			AgentManager amgr = map.getAgentManager();
			ReduceGridAgent start = new EodStartAgent();
			
			Integer result = (Integer) amgr.callReduceAgent(start);
			log.info("Active processors: " + result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void eodProcessed() {
		try {
			log.info("EOD processed");
			Session sess = getSession();
			ObjectMap map = sess.getMap(MAP_NAME);

			AgentManager amgr = map.getAgentManager();
			ReduceGridAgent end = new EodEndAgent();
			
			Integer result = (Integer) amgr.callReduceAgent(end);
			log.info("Active processors: " + result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * getting a Session
	 * @see Session
	 * @return wxs session
	 */
	protected synchronized Session getSession() {

		if (grid == null) {
			try {

				log.info("Creating new connection to the WXS");
				String cep = ObjectGridManagerFactory.getObjectGridManager().getCatalogDomainManager()
						.getDefaultDomainInfo().getClientCatalogServerEndpoints();
				ClientClusterContext ccc = ObjectGridManagerFactory.getObjectGridManager().connect(cep, null, null);

				// Set a custom client properties file
				URL clientPropsURL = Thread.currentThread().getContextClassLoader()
						.getResource("properties/objectGridClient.properties");
				ccc.setClientProperties(GRID_NAME, clientPropsURL);

				// Retrieve the ObjectGrid client connection and cache it.
				log.info("Connection opened, retrieving a grid " + GRID_NAME);
				grid = ObjectGridManagerFactory.getObjectGridManager().getObjectGrid(ccc, GRID_NAME);
				
				return grid.getSession();
			} catch (Exception e) {
				disconnectClient();
				throw new ObjectGridRuntimeException(
						"Unable to connect to catalog service at default endpoint for grid: " + GRID_NAME, e);
			}
		} else {
			try {
				return grid.getSession();
			} catch (Exception e) {
				disconnectClient();
				return getSession();
			}
		}
	}

	/**
	 * Disconnect from the grid, releasing any resources and connections in use.
	 */
	protected synchronized void disconnectClient() {
		log.info("Disconnecting from WXS");
		try {
			if (grid != null)
				grid.destroy();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			grid = null;
		}
	}

	public static synchronized WxsManager getInstance() {
		if (instance == null) {
			instance = new WxsManager();
		}
		return instance;
	}
}
