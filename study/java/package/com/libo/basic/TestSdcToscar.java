import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.exceptions.SdcToscaParserException;
import org.openecomp.sdc.tosca.parser.impl.SdcToscaParserFactory;
import org.openecomp.sdc.toscaparser.api.common.JToscaException;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeSuite;

public class TestSdcToscar{
    static SdcToscaParserFactory factory;
    static ISdcCsarHelper fdntCsarHelper;
    protected static ISdcCsarHelper getCsarHelper(String path, boolean resolveGetInput) throws SdcToscaParserException {
        System.out.println("Parsing CSAR "+path+"...");
        String fileStr1 = TestSdcToscar.class.getClassLoader().getResource(path).getFile();
        File file1 = new File(fileStr1);
        ISdcCsarHelper sdcCsarHelper = factory.getSdcCsarHelper(file1.getAbsolutePath(), resolveGetInput);
        return sdcCsarHelper;
 //   return null;
    }

    public static void main(String[] args) throws SdcToscaParserException,  IOException
    {
        System.out.println("start!");
        String VF_CUSTOMIZATION_UUID = "56179cd8-de4a-4c38-919b-bbc4452d2d73";
   // SdcToscaParserFactory factory;
   // ISdcCsarHelper fdntCsarHelper;
        factory = SdcToscaParserFactory.getInstance();
        fdntCsarHelper = getCsarHelper("csars/service-sunny-flow.csar", false);      
        System.out.println("Done!");

    }
}
