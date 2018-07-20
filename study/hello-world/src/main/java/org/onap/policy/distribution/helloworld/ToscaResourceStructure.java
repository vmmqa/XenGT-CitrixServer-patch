/*-
 * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.policy.distribution.helloworld;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.List;

import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;
import org.onap.sdc.api.results.IDistributionClientDownloadResult;
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcCsarHelperImpl;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.tosca.parser.impl.SdcToscaParserFactory;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.sdc.toscaparser.api.elements.Metadata;

import org.onap.policy.distribution.helloworld.exceptions.ASDCDownloadException;
import org.onap.policy.common.logging.flexlogger.FlexLogger;
import org.onap.policy.common.logging.flexlogger.Logger;


public class ToscaResourceStructure {
	
	Metadata serviceMetadata;
	ISdcCsarHelper sdcCsarHelper;
	List<NodeTemplate> allottedList;
	List<NodeTemplate> networkTypes; 
	List<NodeTemplate> vfTypes; 
	String workloadPerformance;
	boolean isVnfAlreadyInstalled = false;
	String serviceVersion;
	private boolean isDeployedSuccessfully=false;
	
	
	private IArtifactInfo toscaArtifact;
	private static Logger logger = FlexLogger.getLogger(ToscaResourceStructure.class);
	
	public ToscaResourceStructure(){
	}
	
	public void updateResourceStructure(IArtifactInfo artifact) throws ASDCDownloadException {
		
				
		try {
				
			SdcToscaParserFactory factory = SdcToscaParserFactory.getInstance();//Autoclosable
            logger.debug("policy config path is: " + System.getProperty("policy.config.path"));
			
			
			File spoolFile = new File(System.getProperty("policy.config.path") + "ASDC/" + artifact.getArtifactName());
 
            logger.debug("ASDC File path is: " + spoolFile.getAbsolutePath());
			sdcCsarHelper = factory.getSdcCsarHelper(spoolFile.getAbsolutePath());

		}catch(Exception e){
			System.out.println("System out " + e.getMessage());
            logger.error("Exception caught during parser *****LOOK********* " + artifact.getArtifactName()+"ASDC processResourceNotification Exception in processResourceNotification", e);
			throw new ASDCDownloadException ("Exception caught when passing the csar file to the parser ", e);
		}	

			serviceMetadata = sdcCsarHelper.getServiceMetadata();
	
	}
	
	public List<NodeTemplate> getAllottedList() {
		return allottedList;
	}

	public void setAllottedList(List<NodeTemplate> allottedList) {
		this.allottedList = allottedList;
	}

	public ISdcCsarHelper getSdcCsarHelper() {
		return sdcCsarHelper;
	}

	public void setSdcCsarHelper(ISdcCsarHelper sdcCsarHelper) {
		this.sdcCsarHelper = sdcCsarHelper;
	}

	public Metadata getServiceMetadata() {
		return serviceMetadata;
	}
	
	public void setServiceMetadata(Metadata serviceMetadata) {
		this.serviceMetadata = serviceMetadata;
	}
	
	public List<NodeTemplate> getNetworkTypes() {
		return networkTypes;
	}

	public void setNetworkTypes(List<NodeTemplate> networkTypes) {
		this.networkTypes = networkTypes;
	}
	
	public List<NodeTemplate> getVfTypes() {
		return vfTypes;
	}

	public void setVfTypes(List<NodeTemplate> vfTypes) {
		this.vfTypes = vfTypes;
	}


	public IArtifactInfo getToscaArtifact() {
		return toscaArtifact;
	}

	public void setToscaArtifact(IArtifactInfo toscaArtifact) {
		this.toscaArtifact = toscaArtifact;
	}


	public boolean isVnfAlreadyInstalled() {
		return isVnfAlreadyInstalled;
	}

	public void setVnfAlreadyInstalled(boolean isVnfAlreadyInstalled) {
		this.isVnfAlreadyInstalled = isVnfAlreadyInstalled;
	}


	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public boolean isDeployedSuccessfully() {
		return isDeployedSuccessfully;
	}

	public void setSuccessfulDeployment() {
		isDeployedSuccessfully = true;
	}

}
