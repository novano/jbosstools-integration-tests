/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.quickfix.test;


import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.JBossServer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.reddeer.annotation.CDIWizardType;
import org.jboss.tools.cdi.reddeer.annotation.ValidationType;
import org.jboss.tools.cdi.reddeer.validators.IValidationProvider;
import org.jboss.tools.cdi.reddeer.validators.InterceptorValidationProvider;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Interceptor component
 * 
 * @author Jaroslav Jankovic
 */
@JBossServer(state=ServerReqState.PRESENT, type=ServerReqType.AS7_1)
@OpenPerspective(JavaEEPerspective.class)
@CleanWorkspace
public class InterceptorValidationQuickFixTest extends CDITestBase {
	
	private static IValidationProvider validationProvider = new InterceptorValidationProvider();
	
	public IValidationProvider validationProvider() {
		return validationProvider;
	}
	
	// https://issues.jboss.org/browse/JBIDE-7680
	@Test
	public void testSessionAnnotation() {
			
		String className = "Interceptor1";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithStateless.java.cdi"));

		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.STATELESS, getProjectName(), validationProvider());
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7636
	@Test
	public void testNamedAnnotation() {
		
		String className = "Interceptor2";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithNamed.java.cdi"));

		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.NAMED, getProjectName(), validationProvider());
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7683
	@Test
	public void testProducer() {
		
		String className = "Interceptor3";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithProducer.java.cdi"));

		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.PRODUCES, getProjectName(), validationProvider());
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7684
	@Test
	public void testDisposesAnnotation() {
		
		String className = "Interceptor4";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithDisposes.java.cdi"));

		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.DISPOSES, getProjectName(), validationProvider());
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7685
	@Test
	public void testObservesAnnotation() {
		
		String className = "Interceptor5";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithDisposes.java.cdi"));
		
		editResourceUtil.replaceInEditor(className+".java","import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		editResourceUtil.replaceInEditor(className+".java","@Disposes", "@Observes");
		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.OBSERVES, getProjectName(), validationProvider());
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7686
	@Test
	public void testSpecializesAnnotation() {
		
		String className = "Interceptor6";
		
		wizard.createCDIComponentWithContent(CDIWizardType.INTERCEPTOR, className, 
				getPackageName(), null, InterceptorValidationQuickFixTest.class.getResourceAsStream("/resources/quickfix/interceptor/" +
						"InterceptorWithSpecializes.java.cdi"));

		editResourceUtil.replaceInEditor(className+".java","InterceptorComponent", className);
		
		quickFixHelper.checkQuickFix(ValidationType.SPECIALIZES, getProjectName(), validationProvider());
			
	}
	
}
