/**
 * ﻿============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2017-2018 European Software Marketing Ltd.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.policy.distribution.helloworld.notification;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.onap.sdc.api.IDistributionClient;
import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;
import org.onap.sdc.api.results.IDistributionClientResult;
import org.onap.sdc.utils.DistributionActionResultEnum;
import org.onap.sdc.utils.DistributionStatusEnum;
import org.onap.policy.distribution.helloworld.ASDCConfiguration;

/**
 * This class is responsible for publishing the status of actions performed working with artifacts.
 */
class NotificationPublisher {

    private boolean publishingEnabled;

    public NotificationPublisher() {
        Properties configProperties = new Properties();
        try {
            configProperties.load(new FileInputStream(ASDCConfiguration.propertiesFile()));
        } catch (IOException e) {
            String errorMsg = "Failed to load configuration: " + e.getMessage();
        }

        ASDCConfiguration config = new ASDCConfiguration(configProperties);

        publishingEnabled = !config.getASDCConnectionDisabled();
    }

    /**
     * This method is responsible for publishing notification that the download of an artifact failed.
     *
     * @param client The distribution client this notification relates to
     * @param data data about the notification that resulted in this message being created
     * @param artifact the specific artifact to have its distribution status reported on
     * @param errorMessage the error message that is to be reported
     */
    void publishDownloadFailure(IDistributionClient client, INotificationData data, IArtifactInfo artifact,
            String errorMessage) {
        publishDownloadStatus(DistributionStatusEnum.DOWNLOAD_ERROR, client, data, artifact, "failure");

        System.out.print("Failed to download artifact " + artifact.getArtifactName() + ": " + errorMessage);
    }

    private void publishDownloadStatus(DistributionStatusEnum distributionStatusEnum, IDistributionClient client,
            INotificationData data, IArtifactInfo artifact, String result) {
        if (publishingEnabled) {
            IDistributionClientResult sendDownloadStatus = client.sendDownloadStatus(
                    DistributionStatusMessageBuilder.build(client, data, artifact, distributionStatusEnum));

            if (sendDownloadStatus.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
                System.out.print( "Failed to publish download " + result
                        + " status: " + sendDownloadStatus.getDistributionMessageResult());
            }
        } else {
            //logPublishingDisabled(distributionStatusEnum.toString(), result);
        }
    }

    /**
     * This method is responsible for publishing notification that the download of an artifact was successful.
     *
     * @param client The distribution client this notification relates to
     * @param data data about the notification that resulted in this message being created
     * @param artifact the specific artifact to have its distribution status reported on
     */
    void publishDownloadSuccess(IDistributionClient client, INotificationData data, IArtifactInfo artifact) {
        publishDownloadStatus(DistributionStatusEnum.DOWNLOAD_OK, client, data, artifact, "success");

            // @formatter:off
            String msg = "Downloaded artifact:\n" +
                "ArtInfo_Art_Name: " + artifact.getArtifactName() +
                "\nArtInfo_Art_description: " + artifact.getArtifactDescription() +
                "\nArtInfo_Art_CheckSum: " + artifact.getArtifactChecksum() +
                "\nArtInfo_Art_Url: " + artifact.getArtifactURL() +
                "\nArtInfo_Art_Type: " + artifact.getArtifactType() +
                "\nArtInfo_Serv_description: " + data.getServiceDescription() +
                "\nArtInfo_Serv_Name: " + data.getServiceName() +
                "\nGet_serviceVersion: " + data.getServiceVersion() +
                "\nGet_Service_UUID: " + data.getServiceUUID() +
                "\nArtInfo_DistributionId: " + data.getDistributionID();
            System.out.print(msg);
            // @formatter:on
    }

    /**
     * This method is responsible for publishing notification that the deployment of an artifact failed.
     *
     * @param client The distribution client this notification relates to
     * @param data data about the notification that resulted in this message being created
     * @param artifact the specific artifact to have its deployment status reported on
     */
    void publishDeployFailure(IDistributionClient client, INotificationData data, IArtifactInfo artifact) {
        publishDeployStatus(client, data, artifact, DistributionStatusEnum.DEPLOY_ERROR, "failure");
    }

    private void publishDeployStatus(IDistributionClient client, INotificationData data, IArtifactInfo artifact,
            DistributionStatusEnum distributionStatusEnum, String result) {
        if (publishingEnabled) {

            IDistributionClientResult sendStatus = client.sendDeploymentStatus(
                    DistributionStatusMessageBuilder.build(client, data, artifact, distributionStatusEnum));
            if (sendStatus.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
                System.out.print("Failed to publish deploy " + result + " status: " + sendStatus.getDistributionMessageResult());
            }
        } else {
            //logPublishingDisabled(distributionStatusEnum.toString(), result);
        }
    }

    /**
     * This method is responsible for publishing notification that the deployment of an artifact was succesful.
     *
     * @param client The distribution client this notification relates to
     * @param data data about the notification that resulted in this message being created
     * @param artifact the specific artifact to have its deployment status reported on
     */
    void publishDeploySuccess(IDistributionClient client, INotificationData data, IArtifactInfo artifact) {
        publishDeployStatus(client, data, artifact, DistributionStatusEnum.DEPLOY_OK, "success");
    }

    void publishComponentSuccess(IDistributionClient client, INotificationData data) {
        if (publishingEnabled) {
            IDistributionClientResult sendStatus = client.sendComponentDoneStatus(
                    CompDoneStatusMessageBuilder.build(client, data, DistributionStatusEnum.COMPONENT_DONE_OK));

            if (sendStatus.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
             //   logger.error(ModelLoaderMsgs.DISTRIBUTION_EVENT_ERROR,"Failed to publish component done ok: " + sendStatus.getDistributionMessageResult());
            }
        } else {
            //logPublishingDisabled(DistributionStatusEnum.COMPONENT_DONE_OK.toString(), null);
        }
    }

    void publishComponentFailure(IDistributionClient client, INotificationData data, String errorReason) {
        if (publishingEnabled) {
            IDistributionClientResult sendStatus = client.sendComponentDoneStatus(
                    CompDoneStatusMessageBuilder.build(client, data, DistributionStatusEnum.COMPONENT_DONE_ERROR),
                    errorReason);

            if (sendStatus.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
                System.out.print("Failed to publish component done error: " + sendStatus.getDistributionMessageResult());
            }
        } else {
            //logPublishingDisabled(DistributionStatusEnum.COMPONENT_DONE_ERROR.toString(), errorReason);
        }
    }

}
