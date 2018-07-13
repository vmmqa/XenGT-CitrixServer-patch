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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.onap.policy.distribution.helloworld.ASDCConfiguration;
import org.onap.sdc.api.IDistributionClient;
import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;
import org.onap.sdc.api.results.IDistributionClientDownloadResult;
import org.onap.sdc.utils.ArtifactTypeEnum;
import org.onap.sdc.utils.DistributionActionResultEnum;

/**
 * This class is responsible for downloading the artifacts from the ASDC.
 *
 * The downloads can be TOSCA_CSAR files or VNF_CATALOG files.
 *
 * The status of the download is published. The status of the extraction of yml files from a TOSCA_CSAR file is also
 * published as a deployment event.
 *
 * TOSCA_CSAR file artifacts will be converted into XML and returned as model artifacts.
 */
public class ArtifactDownloadManager {


    private IDistributionClient client;
    private NotificationPublisher notificationPublisher;
    private ASDCConfiguration config;

    public ArtifactDownloadManager(IDistributionClient client, ASDCConfiguration config) {
        this.client = client;
        this.config = config;
    }

    /**
     * This method downloads the artifacts from the ASDC.
     *
     * @param data data about the notification that is being processed
     * @param artifacts the specific artifacts found in the data.
     * @param modelArtifacts collection of artifacts for model query specs
     * @param catalogArtifacts collection of artifacts that represent vnf catalog files
     * @return boolean <code>true</code> if the download process was successful otherwise <code>false</code>
     */
    boolean downloadArtifacts(INotificationData data, List<IArtifactInfo> artifacts) {
        boolean success = true;

        for (IArtifactInfo artifact : artifacts) {
            try {
                IDistributionClientDownloadResult downloadResult = downloadIndividualArtifacts(data, artifact);
                processDownloadedArtifacts(artifact, downloadResult, data);
            } catch (DownloadFailureException e) {
                getNotificationPublisher().publishDownloadFailure(client, data, artifact, e.getMessage());
                success = false;
            } catch (Exception e) {
                getNotificationPublisher().publishDeployFailure(client, data, artifact);
                success = false;
            }

            if (!success) {
                break;
            }
        }

        return success;
    }

    private IDistributionClientDownloadResult downloadIndividualArtifacts(INotificationData data,
            IArtifactInfo artifact) throws DownloadFailureException {
        // Grab the current time so we can measure the download time for the metrics log
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        IDistributionClientDownloadResult downloadResult = client.download(artifact);

        if (DistributionActionResultEnum.SUCCESS.equals(downloadResult.getDistributionActionResult())) {
            System.out.print("Downloaded artifact: " + artifact.getArtifactName());
            getNotificationPublisher().publishDownloadSuccess(client, data, artifact);
        } else {
            throw new DownloadFailureException(downloadResult.getDistributionMessageResult());
        }

        return downloadResult;
    }

    private void processDownloadedArtifacts(IArtifactInfo artifactInfo, IDistributionClientDownloadResult downloadResult, INotificationData data){
        if ("TOSCA_CSAR".equalsIgnoreCase(artifactInfo.getArtifactType())) {
        //TBD
            System.out.print("go through to process Tosca Artifacts successfully");
        }  else {
            System.out.print(artifactInfo.getArtifactName()+artifactInfo.getArtifactType());
        }
    }


    private NotificationPublisher getNotificationPublisher() {
        if (notificationPublisher == null) {
            notificationPublisher = new NotificationPublisher();
        }

        return notificationPublisher;
    }

}
