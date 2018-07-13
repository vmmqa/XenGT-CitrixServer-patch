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

import java.util.ArrayList;
import java.util.List;
import org.onap.policy.distribution.helloworld.ASDCConfiguration;
import org.onap.sdc.api.IDistributionClient;
import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;

/**
 * This class is responsible for deploying model and catalog artifacts.
 */
public class ArtifactDeploymentManager {

    private IDistributionClient client;
    private ASDCConfiguration config;
    private NotificationPublisher notificationPublisher;

    public ArtifactDeploymentManager(IDistributionClient client, ASDCConfiguration config) {
        this.client = client;
        this.config = config;
    }

    /**
     * Deploys model and catalog artifacts to A&AI
     *
     * @param data data about the notification that is being processed
     * @param artifacts the specific artifacts found in the data.
     * @return boolean <code>true</code> if all deployments were successful otherwise <code>false</code>
     */
    //TODo
    public boolean deploy(final INotificationData data, final List<IArtifactInfo> artifacts) {


        publishNotifications(data, "TOSCA_CSAR", artifacts, true);

        return true;
    }

    private void publishNotifications(INotificationData data, String filterType, List<IArtifactInfo> artifacts,
            boolean deploymentSuccess) {
        if (deploymentSuccess) {
            getNotificationPublisher().publishComponentSuccess(client, data);
        } else {
            getNotificationPublisher().publishComponentFailure(client, data, "deploy failure");
        }
    }

    private NotificationPublisher getNotificationPublisher() {
        if (notificationPublisher == null) {
            notificationPublisher = new NotificationPublisher();
        }

        return notificationPublisher;
    }

}
