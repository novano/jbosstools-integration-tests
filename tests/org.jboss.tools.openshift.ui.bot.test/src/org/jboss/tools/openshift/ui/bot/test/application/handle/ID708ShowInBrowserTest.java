/*******************************************************************************
 * Copyright (c) 2007-2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v 1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.openshift.ui.bot.test.application.handle;

import static org.junit.Assert.fail;

import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.tools.openshift.ui.bot.test.application.create.IDXXXCreateTestingApplication;
import org.jboss.tools.openshift.reddeer.condition.v2.ApplicationIsDeployedSuccessfully;
import org.jboss.tools.openshift.reddeer.utils.DatastoreOS2;
import org.jboss.tools.openshift.reddeer.utils.OpenShiftLabel;
import org.jboss.tools.openshift.reddeer.view.OpenShiftExplorerView;
import org.junit.Test;

/**
 * Test capabilities of showing an application in web browser.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ID708ShowInBrowserTest extends IDXXXCreateTestingApplication {

	@Test
	public void testShowInBrowser() {
		OpenShiftExplorerView explorer = new OpenShiftExplorerView();
		explorer.getOpenShift2Connection(DatastoreOS2.USERNAME, DatastoreOS2.SERVER).
				getDomain(DatastoreOS2.DOMAIN).getApplication(applicationName).select();
		
		showInBrowser(applicationName);
	}
	
	/**
	 * Assert, that tree item is selected before invoking this method.
	 * 
	 * @param applicationName application name
	 */
	public static void showInBrowser(String applicationName) {
		AbstractWait.sleep(TimePeriod.getCustom(5));
		new ContextMenu(OpenShiftLabel.ContextMenu.SHOW_IN_BROWSER).select();
		
		AbstractWait.sleep(TimePeriod.getCustom(8));
		
		try {
			new WaitUntil(new ApplicationIsDeployedSuccessfully(DatastoreOS2.USERNAME, DatastoreOS2.SERVER,
					DatastoreOS2.DOMAIN, applicationName, "OpenShift"), TimePeriod.LONG);
		} catch (SWTLayerException ex) {
			fail("Browser was not opened successfully.");
		} catch (WaitTimeoutExpiredException ex) {
			fail("Application has not been shown successfully in browser.");
		}
	}
}
