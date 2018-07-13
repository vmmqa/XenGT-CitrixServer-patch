/*-
d * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * Copyright (C) 2017 Huawei Technologies Co., Ltd. All rights reserved.
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.onap.policy.distribution.helloworld.ASDCConfiguration;
import org.onap.policy.distribution.helloworld.ASDCControllerStatus;
import org.onap.policy.distribution.helloworld.exceptions.ASDCControllerException;
import org.onap.policy.distribution.helloworld.exceptions.ASDCDownloadException;
import org.onap.policy.distribution.helloworld.exceptions.ASDCParametersException;
import org.onap.policy.distribution.helloworld.exceptions.ArtifactInstallerException;

import org.onap.sdc.api.IDistributionClient;
import org.onap.sdc.api.consumer.IDistributionStatusMessage;
import org.onap.sdc.api.consumer.IFinalDistrStatusMessage;
import org.onap.sdc.api.consumer.INotificationCallback;
import org.onap.sdc.api.consumer.IStatusCallback;
import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;
import org.onap.sdc.api.notification.IResourceInstance;
import org.onap.sdc.api.notification.IStatusData;
import org.onap.sdc.api.results.IDistributionClientDownloadResult;
import org.onap.sdc.api.results.IDistributionClientResult;
import org.onap.sdc.impl.DistributionClientFactory;
import org.onap.sdc.utils.ArtifactTypeEnum;
import org.onap.sdc.utils.DistributionActionResultEnum;
import org.onap.sdc.utils.DistributionStatusEnum;

public class ASDCController {


    protected boolean isAsdcClientAutoManaged = false;

    protected String controllerName;

   private final class ResourceInstance implements IResourceInstance {
    	       
        @Override
        public String getResourceInstanceName(){
        	return new String();
        }
        
        @Override
        public String getResourceName(){
        	return new String();
        }
        
        @Override
        public String getResourceVersion(){
        	return new String();
        }
        
        @Override
        public String getResourceType(){
        	return new String();
        }
        
        @Override
        public String getResourceUUID(){
        	return new String();
        }
        
        // Method descriptor #10 ()Ljava/util/List;
        @Override
        public java.util.List getArtifacts(){
        	return new ArrayList();
        }
        
        @Override
        public String getResourceInvariantUUID(){
        	return new String();
        }
        
        @Override
        public String getResourceCustomizationUUID(){
        	return new String();
        }
        
        @Override
        public String getCategory(){
        	return new String();
        }
        
        @Override
        public String getSubcategory(){
        	return new String();
        }
    	
    }
    
    /**
     * Inner class for Notification callback
     *
     *
     */
    private final class ASDCNotificationCallBack implements INotificationCallback {

        private ASDCController asdcController;

        ASDCNotificationCallBack (ASDCController controller) {
            asdcController = controller;
        }

        /**
         * This method can be called multiple times at the same moment.
         * The controller must be thread safe !
         */
        @Override
        public void activateCallback (INotificationData iNotif) {
            long startTime = System.currentTimeMillis ();
            String event = "Receive a callback notification in ASDC, nb of resources: " + iNotif.getResources ().size ();
            asdcController.treatNotification (iNotif);
        }
    }
    

    // ***** Controller STATUS code

    protected int nbOfNotificationsOngoing = 0;

    public int getNbOfNotificationsOngoing () {
        return nbOfNotificationsOngoing;
    }

    private ASDCControllerStatus controllerStatus = ASDCControllerStatus.STOPPED;

    protected synchronized final void changeControllerStatus (ASDCControllerStatus newControllerStatus) {
        switch (newControllerStatus) {

            case BUSY:
                ++this.nbOfNotificationsOngoing;
                this.controllerStatus = newControllerStatus;
                break;

            case IDLE:
                if (this.nbOfNotificationsOngoing > 1) {
                    --this.nbOfNotificationsOngoing;
                } else {
                    this.nbOfNotificationsOngoing = 0;
                    this.controllerStatus = newControllerStatus;
                }

                break;
            default:
                this.controllerStatus = newControllerStatus;
                break;

        }
    }

    public synchronized final ASDCControllerStatus getControllerStatus () {
        return this.controllerStatus;
    }

    // ***** END of Controller STATUS code
    protected ASDCConfiguration asdcConfig;
    private IDistributionClient distributionClient;

    public ASDCController (String controllerConfigName) {
        isAsdcClientAutoManaged = true;
        this.controllerName = controllerConfigName;
    }

    public ASDCController (String controllerConfigName,IDistributionClient asdcClient) {
        distributionClient = asdcClient;
        this.controllerName = controllerConfigName;
    }

    /**
     * This method refresh the ASDC Controller config and restart the client.
     *
     * @return true if config has been reloaded, false otherwise
     * @throws ASDCControllerException If case of issue with the init or close called during the config reload
     * @throws ASDCParametersException If there is an issue with the parameters
     * @throws IOException In case of the key file could not be loaded properly
     */
/*    public boolean updateConfigIfNeeded () throws ASDCParametersException, ASDCControllerException, IOException {

        try {
            if (this.asdcConfig != null && this.asdcConfig.hasASDCConfigChanged ()) {
                this.closeASDC ();
                this.asdcConfig.refreshASDCConfig ();
                this.initASDC ();
                return true;
            } else {
                return false;
            }
        } catch (ASDCParametersException ep) {
            // Try to close it at least to make it consistent with the file specified
            // We cannot let it run with a different config file, even if it's bad.
            // This call could potentially throw a ASDCController exception if the controller is currently BUSY.
            this.closeASDC ();

            throw ep;
        }
    }*/

    /**
     * This method initializes the ASDC Controller and the ASDC Client.
     *
     * @throws ASDCControllerException It throws an exception if the ASDC Client cannot be instantiated or if an init
     *         attempt is done when already initialized
     * @throws ASDCParametersException If there is an issue with the parameters provided
     * @throws IOException In case of issues when trying to load the key file
     */
    public void initASDC () throws ASDCControllerException, ASDCParametersException, IOException {
        String event = "Initialize the ASDC Controller";
        if (this.getControllerStatus () != ASDCControllerStatus.STOPPED) {
            String endEvent = "The controller is already initialized, call the closeASDC method first";
            throw new ASDCControllerException (endEvent);
        }

        if (asdcConfig == null) {
             String configDir="";
             ASDCConfiguration.setConfigHome(configDir);
             Properties configProperties = new Properties();
             configProperties.load(new FileInputStream(configDir + "/" + "model-loader.properties"));
            asdcConfig = new ASDCConfiguration (configProperties);

        }
        // attempt to refresh during init as MsoProperties is may be pointing to an old file
        // Be careful this is static in MsoProperties
        //asdcConfig.refreshASDCConfig ();

        if (this.distributionClient == null) {
            distributionClient = DistributionClientFactory.createDistributionClient ();
        }
        long initStartTime = System.currentTimeMillis ();
        IDistributionClientResult result = this.distributionClient.init (asdcConfig,
                                                                         new ASDCNotificationCallBack (this));
        if (!result.getDistributionActionResult ().equals (DistributionActionResultEnum.SUCCESS)) {
            String endEvent = "ASDC distribution client init failed with reason:"
                              + result.getDistributionMessageResult ();
            asdcConfig = null;

            this.changeControllerStatus (ASDCControllerStatus.STOPPED);
            throw new ASDCControllerException ("Initialization of the ASDC Controller failed with reason: "
                                               + result.getDistributionMessageResult ());
        }

        long clientstartStartTime = System.currentTimeMillis ();
        result = this.distributionClient.start ();
        if (!result.getDistributionActionResult ().equals (DistributionActionResultEnum.SUCCESS)) {
            String endEvent = "ASDC distribution client start failed with reason:"
                              + result.getDistributionMessageResult ();
            asdcConfig = null;
            this.changeControllerStatus (ASDCControllerStatus.STOPPED);
            throw new ASDCControllerException ("Startup of the ASDC Controller failed with reason: "
                                               + result.getDistributionMessageResult ());
        }


        this.changeControllerStatus (ASDCControllerStatus.IDLE);
    }

    /**
     * This method closes the ASDC Controller and the ASDC Client.
     *
     * @throws ASDCControllerException It throws an exception if the ASDC Client cannot be closed because
     *         it's currently BUSY in processing notifications.
     */
    public void closeASDC () throws ASDCControllerException {

        if (this.getControllerStatus () == ASDCControllerStatus.BUSY) {
            throw new ASDCControllerException ("Cannot close the ASDC controller as it's currently in BUSY state");
        }
        if (this.distributionClient != null) {
            this.distributionClient.stop ();
            // If auto managed we can set it to Null, ASDCController controls it.
            // In the other case the client of this class has specified it, so we can't reset it
            if (isAsdcClientAutoManaged) {
                // Next init will initialize it with a new ASDC Client
                this.distributionClient = null;
            }

        }
        this.changeControllerStatus (ASDCControllerStatus.STOPPED);
    }

    private final static String UUID_PARAM = "(UUID:";

    private IDistributionClientDownloadResult downloadTheArtifact (IArtifactInfo artifact,
                                                                   String distributionId) throws ASDCDownloadException {

        IDistributionClientDownloadResult downloadResult;


        try {
            downloadResult = distributionClient.download (artifact);
            if (null == downloadResult) {
            	return downloadResult;
            }
        } catch (RuntimeException e) {
            this.sendASDCNotification (NotificationType.DOWNLOAD,
                                       artifact.getArtifactURL (),
                                       asdcConfig.getConsumerID (),
                                       distributionId,
                                       DistributionStatusEnum.DOWNLOAD_ERROR,
                                       e.getMessage (),
                                       System.currentTimeMillis ());

            throw new ASDCDownloadException ("Exception caught when downloading the artifact", e);
        }

        if (DistributionActionResultEnum.SUCCESS.equals(downloadResult.getDistributionActionResult ())) {


        } else {

            this.sendASDCNotification (NotificationType.DOWNLOAD,
                                       artifact.getArtifactURL (),
                                       asdcConfig.getConsumerID (),
                                       distributionId,
                                       DistributionStatusEnum.DOWNLOAD_ERROR,
                                       downloadResult.getDistributionMessageResult (),
                                       System.currentTimeMillis ());

            throw new ASDCDownloadException ("Artifact " + artifact.getArtifactName ()
                                             + " could not be downloaded from ASDC URL "
                                             + artifact.getArtifactURL ()
                                             + UUID_PARAM
                                             + artifact.getArtifactUUID ()
                                             + ")"
                                             + System.lineSeparator ()
                                             + "Error message is "
                                             + downloadResult.getDistributionMessageResult ()
                                             + System.lineSeparator ());

        }

        this.sendASDCNotification (NotificationType.DOWNLOAD,
                                   artifact.getArtifactURL (),
                                   asdcConfig.getConsumerID (),
                                   distributionId,
                                   DistributionStatusEnum.DOWNLOAD_OK,
                                   null,
                                   System.currentTimeMillis ());
        return downloadResult;

    }

    private void writeArtifactToFile (IArtifactInfo artifact,
    		IDistributionClientDownloadResult resultArtifact) throws ASDCDownloadException {

    	File spoolFile = new File(System.getProperty("mso.config.path") + "/ASDC" + "/" + artifact.getArtifactName()); 
    	  	
    	
    	byte[] payloadBytes = resultArtifact.getArtifactPayload();
    	
    	try {

    		FileOutputStream outFile = new FileOutputStream(System.getProperty("mso.config.path") + "/ASDC" + "/" + artifact.getArtifactName());
    		outFile.write(payloadBytes, 0, payloadBytes.length);
    		outFile.close();
    		} catch (Exception e) { 
            } 
    	
    }


/*    private void sendDeployNotificationsForResource(VfResourceStructure vfResourceStructure,DistributionStatusEnum distribStatus, String errorReason) {

    	for (IArtifactInfo artifactInfo : vfResourceStructure.getResourceInstance().getArtifacts()) {

    		if ((DistributionStatusEnum.DEPLOY_OK.equals(distribStatus) && !artifactInfo.getArtifactType().equalsIgnoreCase("OTHER"))
    				// This could be NULL if the artifact is a VF module artifact, this won't be present in the MAP
    				&& vfResourceStructure.getArtifactsMapByUUID().get(artifactInfo.getArtifactUUID()) != null
    				&& vfResourceStructure.getArtifactsMapByUUID().get(artifactInfo.getArtifactUUID()).getDeployedInDb() == 0) {
    			this.sendASDCNotification (NotificationType.DEPLOY,
	    				artifactInfo.getArtifactURL (),
	                  asdcConfig.getConsumerID (),
	                  vfResourceStructure.getNotification().getDistributionID(),
	                  DistributionStatusEnum.DEPLOY_ERROR,
	                  "The artifact has not been used by the modules defined in the resource",
	                  System.currentTimeMillis ());
    		} else {
	    		this.sendASDCNotification (NotificationType.DEPLOY,
	    				artifactInfo.getArtifactURL (),
	                  asdcConfig.getConsumerID (),
	                  vfResourceStructure.getNotification().getDistributionID(),
	                  distribStatus,
	                  errorReason,
	                  System.currentTimeMillis ());
    		}
    	}
    }
    
    private void sendCsarDeployNotification(INotificationData iNotif, VfResourceStructure resourceStructure, ToscaResourceStructure toscaResourceStructure, boolean deploySuccessful, String errorReason) {
    	
    			IArtifactInfo csarArtifact = toscaResourceStructure.getToscaArtifact();
    			
    			if(deploySuccessful){
    				
     	    		this.sendASDCNotification (NotificationType.DEPLOY,
     	 	    			  csarArtifact.getArtifactURL (),
     		                  asdcConfig.getConsumerID (),
     		                  resourceStructure.getNotification().getDistributionID(),
     		                  DistributionStatusEnum.DEPLOY_OK,
     		                  errorReason,
     		                  System.currentTimeMillis ());
    				
    			} else {
    				
    				this.sendASDCNotification (NotificationType.DEPLOY,
   	 	    			  csarArtifact.getArtifactURL (),
   		                  asdcConfig.getConsumerID (),
   		                  resourceStructure.getNotification().getDistributionID(),
   		                  DistributionStatusEnum.DEPLOY_ERROR,
   		                  errorReason,
   		                  System.currentTimeMillis ());
    				
    			}
    }*/
    
/*    private void deployResourceStructure (VfResourceStructure resourceStructure, ToscaResourceStructure toscaResourceStructure) throws ArtifactInstallerException {

        try {
        	String resourceType = resourceStructure.getResourceInstance().getResourceType();
        	String category = resourceStructure.getResourceInstance().getCategory();
        	if("VF".equals(resourceType) && !"Allotted Resource".equalsIgnoreCase(category)){
        		resourceStructure.createVfModuleStructures();
        	}
        	

        	toscaInstaller.installTheResource(toscaResourceStructure, resourceStructure);
        	        				

        } catch (ArtifactInstallerException e) {
        	throw e;
        }

        if (resourceStructure.isDeployedSuccessfully() || toscaResourceStructure.isDeployedSuccessfully()) {
	        sendDeployNotificationsForResource(resourceStructure,DistributionStatusEnum.DEPLOY_OK ,null);
        }

    }*/
    

    private enum NotificationType {
    	DOWNLOAD, DEPLOY
    }

    private void sendASDCNotification (NotificationType notificationType,
                                       String artifactURL,
                                       String consumerID,
                                       String distributionID,
                                       DistributionStatusEnum status,
                                       String errorReason,
                                       long timestamp) {

        String event = "Sending " + notificationType.name ()
                       + "("
                       + status.name ()
                       + ")"
                       + " notification to ASDC for artifact:"
                       + artifactURL;

        if (errorReason != null) {
        	event=event+"("+errorReason+")";
        }

        long subStarttime = System.currentTimeMillis ();
        String action = "";
        try {
            IDistributionStatusMessage message = new DistributionStatusMessage (artifactURL,
                                                                                consumerID,
                                                                                distributionID,
                                                                                status,
                                                                                timestamp);

            switch (notificationType) {
                case DOWNLOAD:
                    if (errorReason != null) {
                        this.distributionClient.sendDownloadStatus (message, errorReason);
                    } else {
                        this.distributionClient.sendDownloadStatus (message);
                    }
                    action = "sendDownloadStatus";
                    break;
                case DEPLOY:
                    if (errorReason != null) {
                        this.distributionClient.sendDeploymentStatus (message, errorReason);
                    } else {
                        this.distributionClient.sendDeploymentStatus (message);
                    }
                    action = "sendDeploymentdStatus";
                    break;
                default:
                	break;
            }
        } catch (RuntimeException e) {
            // TODO: May be a list containing the unsent notification should be
            // kept
        }
    }
    
    private void sendFinalDistributionStatus (
    		String distributionID,
    		DistributionStatusEnum status,
    		String errorReason) {



    	long subStarttime = System.currentTimeMillis ();
    	try {
    		
    		
    		IFinalDistrStatusMessage finalDistribution = new FinalDistributionStatusMessage(distributionID,status,subStarttime, asdcConfig.getConsumerID());
    		
    		if(errorReason == null){
    			this.distributionClient.sendFinalDistrStatus(finalDistribution);
    		}else{
    			this.distributionClient.sendFinalDistrStatus(finalDistribution, errorReason);
    		}
    		
 
    	} catch (RuntimeException e) {
    		// TODO: May be a list containing the unsent notification should be
    		// kept
    	}
    }

    public void treatNotification (INotificationData iNotif) {

    	int noOfArtifacts = 0;
    	for (IResourceInstance resource : iNotif.getResources ()) {
    		noOfArtifacts += resource.getArtifacts ().size ();
    	}

        try {
			this.changeControllerStatus(ASDCControllerStatus.BUSY);
			
			
						
			// Process only the Resource artifacts in MSO
			for (IResourceInstance resource : iNotif.getResources()) {
				
				// We process only VNF(VF), Network(VL) and PNF resources on MSO Side
				//if ("VF".equals(resource.getResourceType()) || "VL".equals(resource.getResourceType()) || "PNF".equals(resource.getResourceType())){
				this.processResourceNotification(iNotif,resource);
				//}

			}
			
			//Handle services without any resources	
			if (iNotif.getResources() == null || iNotif.getResources().size() < 1){
				
				this.processResourceNotification(iNotif, new ResourceInstance());
			}
			
            //TODO add watchdog or get the distribute status
        } catch (Exception e) {
            
             sendFinalDistributionStatus(iNotif.getDistributionID(), DistributionStatusEnum.DISTRIBUTION_COMPLETE_ERROR, e.getMessage());
            
        } finally {
            this.changeControllerStatus (ASDCControllerStatus.IDLE);
        }
    }


    private void processResourceNotification (INotificationData iNotif,IResourceInstance resource) {
		// For each artifact, create a structure describing the VFModule in a ordered flat level
    	ToscaResourceStructure toscaResourceStructure = new ToscaResourceStructure();
    	boolean deploySuccessful = true;
    	String errorMessage = null;

		try {
				for (IArtifactInfo artifact : resource.getArtifacts()) {

						IDistributionClientDownloadResult resultArtifact = this.downloadTheArtifact(artifact,
								iNotif.getDistributionID());

						if (resultArtifact != null) {
                            //TODO handler Artifact
						}

				}
				this.processCsarServiceArtifacts(iNotif, toscaResourceStructure);
				
                //TODO handler deploy
				/*try{
				
					this.deployResourceStructure(resourceStructure, toscaResourceStructure);
					
				} catch(ArtifactInstallerException e){
					deploySuccessful = false;
					errorMessage = e.getMessage();
				}
												
					this.sendCsarDeployNotification(iNotif, resourceStructure, toscaResourceStructure, deploySuccessful, errorMessage);*/
					


		} catch (Exception e) {
            System.out.println("Whats the error " + e.getMessage());
            
		}
    }

    private void processCsarServiceArtifacts (INotificationData iNotif, ToscaResourceStructure toscaResourceStructure) {
    	
    	List<IArtifactInfo> serviceArtifacts = iNotif.getServiceArtifacts();
    	
    		for(IArtifactInfo artifact : serviceArtifacts){
    		
    			//if(artifact.getArtifactType().equals(ArtifactTypeEnum.TOSCA_CSAR.toString())){
    			if(artifact.getArtifactType().equals("TOSCA_CSAR")){
 				
    				try{
    					
    					toscaResourceStructure.setToscaArtifact(artifact);
    					
    					IDistributionClientDownloadResult resultArtifact = this.downloadTheArtifact(artifact,iNotif.getDistributionID());
    					
    					writeArtifactToFile(artifact, resultArtifact);
    					
    					toscaResourceStructure.updateResourceStructure(artifact);
    					
    					toscaResourceStructure.setServiceVersion(iNotif.getServiceVersion());
    					

    				} catch(Exception e){
    					System.out.println("Whats the error " + e.getMessage());
    				}
    			}
    				
    		}
    }
    
    private static final String UNKNOWN="Unknown";

    /**
     * @return the address of the ASDC we are connected to.
     */
    public String getAddress () {
        if (asdcConfig != null) {
            return asdcConfig.getAsdcAddress ();
        }
        return UNKNOWN;
    }

    /**
     * @return the environment name of the ASDC we are connected to.
     */
    public String getEnvironment () {
        if (asdcConfig != null) {
            return asdcConfig.getEnvironmentName ();
        }
        return UNKNOWN;
    }

}
