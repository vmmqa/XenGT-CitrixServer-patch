package org.onap.policy.distribution.helloworld;

import java.io.File;
import java.util.*;

import org.onap.sdc.toscaparser.api.*;
import org.onap.sdc.toscaparser.api.elements.*;
import org.onap.sdc.toscaparser.api.parameters.*;
import org.onap.sdc.tosca.parser.api.*;
import org.onap.sdc.tosca.parser.impl.*;
import org.onap.sdc.tosca.parser.exceptions.SdcToscaParserException;

import org.junit.*;
public class ToscaWrapperTest
{
    @Test
    public void updateResourceStructureTest(){

        ToscaWrapper toscaWrapper = new ToscaWrapper();
        toscaWrapper.updateResourceStructure("service-sunny-flow.csar");
        ISdcCsarHelper helper = toscaWrapper.getSdcCsarHelper(); 

//    List<NodeTemplate> serviceNodeTemplatesByType = helper.getServiceNodeTemplatesByType("org.openecomp.nodes.ForwardingPath");
        Metadata serviceMetadata =   helper.getServiceMetadata();
        System.out.println("the information of Metadata ="+serviceMetadata);

        List<Input> serviceInputs = helper.getServiceInputs();
        System.out.println("the size of Input="+serviceInputs.size());
        for (Input input : serviceInputs){
            System.out.println("name="+input.getName()+",type="+input.getType()+",requried="+input.isRequired()+",getDescription="+input.getDescription());
        }
 
      //get service NodeTemplate
      //getServiceNodeTemplateBy[NodeName|SdcType|Type]
      //getServiceNodeTemplates
      //    List<NodeTemplate> typeList = helper.getServiceNodeTemplatesByType("org.openecomp.resource.vf.Fdnt");
      //    NodeTemplate nameNode = helper.getServiceNodeTemplateByNodeName();
      //    List<NodeTemplate> sdctypeList = getServiceNodeTemplateBySdcType();
      List<NodeTemplate> node1 = helper.getServiceNodeTemplatesByType("org.openecomp.resource.vf.ContrailRoute");
      System.out.println("size="+node1.size()+",the meta name="+node1.get(0).getMetaData().getValue("name"));
  
      List<NodeTemplate> node2 = helper.getServiceNodeTemplateBySdcType(SdcTypes.VF);
      System.out.println("size="+node2.size()+",the meta name="+node2.get(0).getMetaData().getValue("name"));
      
      NodeTemplate node3 = helper.getServiceNodeTemplateByNodeName("Allotted resource Contrail Route 1");
      System.out.println("name="+node3.getName()+",the meta name="+node3.getMetaData().getValue("name"));
     
      List<NodeTemplate> allList = helper.getServiceNodeTemplates();
      System.out.println("the size of allList="+allList.size());
      for(NodeTemplate node : allList){
         System.out.println("name="+node.getName()); 
         System.out.println("MedtaData="+node.getMetaData()); 
         System.out.println("type="+node.getType()); 
         ArrayList<Property> properties = node.getPropertiesObjects();
         System.out.println("size of properties="+node.getPropertiesObjects().size()); 
         for(Property property: properties){
              System.out.println("property="+property.getName());
          }
      }
      
      //getNodeTemplatePropertyLeafValue(org.onap.sdc.toscaparser.api.NodeTemplate nodeTemplate, String pathToPropertyLeafValue)
      NodeTemplate nd_exVL = helper.getServiceNodeTemplateByNodeName("exVL");
      String network_role = helper.getNodeTemplatePropertyLeafValue(nd_exVL,"network_role");
      String use_ipv4 = helper.getNodeTemplatePropertyLeafValue(nd_exVL,"network_assignments#ipv4_subnet_default_assignment#use_ipv4");
      System.out.println("the network_role="+network_role+"the use_ipv4="+use_ipv4);
     
  
      //org.onap.sdc.toscaparser.api.CapabilityAssignments getCapabilitiesOf(org.onap.sdc.toscaparser.api.NodeTemplate nt)
      //capabilityAssignments.getCapabilityByName("DNT_FW_RHRG.binding_DNT_FW_INT_DNS_TRUSTED_RVMI")
      List<NodeTemplate> vfs = helper.getServiceVfList();
      CapabilityAssignments capabilityAssignments = helper.getCapabilitiesOf(vfs.get(0));
      System.out.println("the content ="+capabilityAssignments.getCapabilityByName("DNT_FW_RHRG.binding_DNT_FW_INT_DNS_TRUSTED_RVMI"));
  
  
      //each service includes several  NodeTemplate. 
      //each NodeTemplate includes several resources, each resource is a NodeTemplate
      List<NodeTemplate> cps = helper.getNodeTemplateBySdcType(vfs.get(0), SdcTypes.CP);
      RequirementAssignments requirementAssignments = helper.getRequirementsOf(cps.get(0));
      RequirementAssignment requirementAssignment = requirementAssignments.getRequirementsByName("binding").getAll().get(0);
      System.out.println("the content="+requirementAssignment);
      System.out.println("the name of requirement="+requirementAssignment.getName()+",the nodeName="+requirementAssignment.getNodeTemplateName());
      System.out.println("the name of capabilityName="+requirementAssignment.getCapabilityName()+",the relationship="+requirementAssignment.getRelationship());
    
      return ;
}
    public ToscaWrapperTest(){
    }

    public static void main(String[] args) throws SdcToscaParserException{
    }


}
