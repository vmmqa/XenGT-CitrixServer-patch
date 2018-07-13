package org.onap.policy.distribution.helloworld;

import java.io.File;
import java.util.*;

import org.onap.sdc.toscaparser.api.*;
import org.onap.sdc.toscaparser.api.elements.*;
import org.onap.sdc.toscaparser.api.parameters.*;
import org.onap.sdc.tosca.parser.api.*;
import org.onap.sdc.tosca.parser.impl.*;
import org.onap.sdc.tosca.parser.exceptions.SdcToscaParserException;

public class ToscaWrapper
{
    static SdcToscaParserFactory factory;
    static ISdcCsarHelper helper;

    public String  updateResourceStructure(String ArtifactName)
    {
    try {
        factory = SdcToscaParserFactory.getInstance();
        System.setProperty("policy.config.path", "src/test/resources/");
        File spoolFile = new File(System.getProperty("policy.config.path") + "ASDC/" + ArtifactName);
        System.out.println("PATH IS " + spoolFile.getAbsolutePath());
        helper = factory.getSdcCsarHelper(spoolFile.getAbsolutePath());

    } catch(Exception e){
        System.out.println("System out " + e.getMessage());
    }
    
    return "Hello Maven\n";
    }

    public ToscaWrapper(){
    }

    public ISdcCsarHelper getSdcCsarHelper() {
        return helper;
    }

    public void setSdcCsarHelper(ISdcCsarHelper sdcCsarHelper) {
      this.helper = sdcCsarHelper;
    }
}
