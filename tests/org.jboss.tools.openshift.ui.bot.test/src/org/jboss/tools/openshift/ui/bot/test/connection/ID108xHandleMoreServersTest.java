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
package org.jboss.tools.openshift.ui.bot.test.connection;

import static org.junit.Assert.fail;

import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.tools.openshift.reddeer.utils.DatastoreOS2;
import org.jboss.tools.openshift.reddeer.view.OpenShiftExplorerView;
import org.junit.Test;

/**
 * OpenShift Enterprise only! Test capabilities to work with more servers.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ID108xHandleMoreServersTest {

	@Test
	@RunIf(conditionClass = OSE2CredentialsExist.class)
	public void testHandleMoreServers() {
		OpenShiftExplorerView explorer = new OpenShiftExplorerView();

		explorer.openConnectionShell();
		explorer.connectToOpenShift2(DatastoreOS2.X_SERVER,
				DatastoreOS2.X_USERNAME, DatastoreOS2.X_PASSWORD, false, false, false);

		try {
			explorer.getOpenShift2Connection(DatastoreOS2.X_USERNAME, DatastoreOS2.X_SERVER);
			// PASS
		} catch (JFaceLayerException ex) {
			fail("Connection has not been created. It is not listed in OpenShift explorer");
		}
	}	
}
