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
package org.onap.policy.distribution.helloworld;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import org.onap.policy.distribution.helloworld.ASDCConfiguration;
import org.onap.policy.distribution.helloworld.notification.ArtifactDeploymentManager;
import org.onap.policy.distribution.helloworld.notification.ArtifactDownloadManager;
import org.onap.policy.distribution.helloworld.notification.EventCallback;
import org.onap.sdc.api.IDistributionClient;
import org.onap.sdc.api.notification.IArtifactInfo;
import org.onap.sdc.api.notification.INotificationData;
import org.onap.sdc.api.results.IDistributionClientResult;
import org.onap.sdc.impl.DistributionClientFactory;
import org.onap.sdc.utils.DistributionActionResultEnum;

/**
 * Service class in charge of managing the negotiating model loading capabilities between AAI and an ASDC.
 */
public class ModelLoaderService {


    protected static final String FILESEP =
            (System.getProperty("file.separator") == null) ? "/" : System.getProperty("file.separator");

    private String configDir;
    private IDistributionClient client;
    private ASDCConfiguration config;

    /**
     * Responsible for loading configuration files and calling initialization.
     */
    protected void start() {
        // Load model loader system configuration
        ASDCConfiguration.setConfigHome(configDir);
        Properties configProperties = new Properties();
        try {
            configProperties.load(new FileInputStream(configDir + FILESEP + "model-loader.properties"));
            config = new ASDCConfiguration(configProperties);
            if (!config.getASDCConnectionDisabled()) {
                initSdcClient();
            }
        } catch (IOException e) {
            String errorMsg = "Failed to load configuration: " + e.getMessage();
        }
    }

    /**
     * Responsible for stopping the connection to the distribution client before the resource is destroyed.
     */
    protected void preShutdownOperations() {
        if (client != null) {
            client.stop();
        }
    }

    /**
     * Responsible for loading configuration files, initializing model distribution clients, and starting them.
     */
    protected void initSdcClient() {
        // Initialize distribution client
        client = DistributionClientFactory.createDistributionClient();
        EventCallback callback = new EventCallback(client, config);

        IDistributionClientResult initResult = client.init(config, callback);

        if (initResult.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
            String errorMsg = "Failed to initialize distribution client: " + initResult.getDistributionMessageResult();
            System.out.print(errorMsg);

        } else {
            // Start distribution client
            System.out.print( "Starting distribution client...");
            IDistributionClientResult startResult = client.start();
            if (startResult.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
                String errorMsg = "Failed to start distribution client: " + startResult.getDistributionMessageResult();
                System.out.print(errorMsg);

            } else {
                System.out.print("Connection to SDC established");
            }
        }
 //       Runtime.getRuntime().addShutdownHook(new Thread(this::preShutdownOperations));
    }

}
