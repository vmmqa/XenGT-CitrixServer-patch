package com.intel;

/**
 * Hello world!
 *
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.message.BasicNameValuePair;
//import org.onap.msb.sdk.discovery.common.RouteException;

public class App {
	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://10.239.40.43:8080/test2/";

	private static final String POST_URL = "http://10.239.40.43:8080/test2/home1";

	public  static void test2( ) throws IOException {
        try
        {
            // setup the socket address
            InetSocketAddress address = new InetSocketAddress ( InetAddress.getLocalHost (), config.getHttpsPort () );
        
            // initialise the HTTPS server
            HttpsServer httpsServer = HttpsServer.create ( address, 0 );
            SSLContext sslContext = SSLContext.getInstance ( "TLS" );
        
            // initialise the keystore
            char[] password = "simulator".toCharArray ();
            KeyStore ks = KeyStore.getInstance ( "JKS" );
            FileInputStream fis = new FileInputStream ( "lig.keystore" );
            ks.load ( fis, password );
        
            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance ( "SunX509" );
            kmf.init ( ks, password );
        
            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance ( "SunX509" );
            tmf.init ( ks );
        
            // setup the HTTPS context and parameters
            sslContext.init ( kmf.getKeyManagers (), tmf.getTrustManagers (), null );
            httpsServer.setHttpsConfigurator ( new HttpsConfigurator( sslContext )
            {
                public void configure ( HttpsParameters params )
                {
                    try
                    {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault ();
                        SSLEngine engine = c.createSSLEngine ();
                        params.setNeedClientAuth ( false );
                        params.setCipherSuites ( engine.getEnabledCipherSuites () );
                        params.setProtocols ( engine.getEnabledProtocols () );
        
                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters ();
                        params.setSSLParameters ( defaultSSLParameters );
                    }
                    catch ( Exception ex )
                    {
                        ILogger log = new LoggerFactory ().getLogger ();
                        log.exception ( ex );
                        log.error ( "Failed to create HTTPS port" );
                    }
                }
            } );
            LigServer server = new LigServer ( httpsServer );
            joinableThreadList.add ( server.getJoinableThread () );
        }
        catch ( Exception exception )
        {
            log.exception ( exception );
            log.error ( "Failed to create HTTPS server on port " + config.getHttpsPort () + " of localhost" );
        }
    }
	public  static void test1( ) throws IOException {
		HttpPost httpPost = new HttpPost(POST_URL);
		httpPost.addHeader("Authorization", "Basic QlBFTENsaWVudDpwYXNzd29yZDEk");
		//httpPost.addHeader("Content-type", "application/soap+xml");
		//httpPost.addHeader("Content-type", "application/soap+xml");
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                urlParameters.add(new BasicNameValuePair("user", "Pankaj Kumar"));

                HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
                httpPost.setEntity(postParams);

//        String url = "http://mso:8080/dbadapters/RequestsDbAdapter";
 //       HttpPost httpPost = new HttpPost(url);
 //       httpPost.addHeader("Authorization", "Basic QlBFTENsaWVudDpwYXNzd29yZDEk");
  //      httpPost.addHeader("Content-type", "application/soap+xml");
    //    String getBody = getGetStringBody(serviceId, operationId, resourceTemplateUUID);
      //  httpPost.setEntity(new StringEntity(getBody, ContentType.APPLICATION_XML));
        //String result = httpPost(url, httpPost);

		//sendPOST();               
		HttpGet httpGet = new HttpGet(GET_URL);
                //httpGet.addHeader("User-Agent", USER_AGENT);
                httpGet.addHeader("User-Agent", "test");
		httpGet(GET_URL, httpGet);

		httpPost(POST_URL,httpPost);
		System.out.println("POST DONE");
	}
    //protected String httpPost(String url, HttpPost httpPost) throws RouteException {
    protected static String httpPost(String url, HttpPost httpPost)  {
        System.out.println("AbstractSdncOperationTask.httpPost begin!");
        String result = null;

        String errorMsg;
        label91: {
            try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
                CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
                result = EntityUtils.toString(closeableHttpResponse.getEntity());
                System.out.println("result = {}"+result);
//                LOGGER.info(MessageEnum.RA_RESPONSE_FROM_SDNC, result.toString(), "SDNC", "");
                if(closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                    System.out.println("exception: fail for status code = {}"+ closeableHttpResponse.getStatusLine().getStatusCode());
                  //throw new RouteException(result, "SERVICE_GET_ERR");
                }

                closeableHttpResponse.close();
                break label91;
            } catch (IOException var19) {
                errorMsg = url + ":httpPostWithJSON connect faild";
                System.out.println("exception: POST_CONNECT_FAILD : {}"+errorMsg);
                //throwsRouteException(errorMsg, var19, "POST_CONNECT_FAILD");
            } 
        }

        System.out.println("AbstractSdncOperationTask.httpPost end!");
        return result;
    }
    //private String httpGet(String url, HttpGet httpGet) throws RouteException {
    private static String httpGet(String url, HttpGet httpGet)  {
        System.out.println("AbstractSdncOperationTask.httpGet begin!");
        String result = "";
        String errorMsg;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
		CloseableHttpResponse e = httpClient.execute(httpGet);
		result = EntityUtils.toString(e.getEntity());
		System.out.println("result = {}"+result);
		if (e.getStatusLine().getStatusCode() != 200) {
			System.out.println("exception: fail for status code = {}"+e.getStatusLine().getStatusCode());
        //                throw new RouteException(result, "SERVICE_GET_ERR");
                    }

                    e.close();
	} catch (ClientProtocolException var21) {
		errorMsg = url + ":httpGetWithJSON connect faild";
		System.out.println("exception: GET_CONNECT_FAILD {}"+errorMsg);
		//throwsRouteException(errorMsg, var21, "GET_CONNECT_FAILD");
	} catch (IOException var22) {
		errorMsg = url + ":httpGetWithJSON connect faild";
		System.out.println("exception: GET_CONNECT_FAILD {}"+errorMsg);
		//throwsRouteException(errorMsg, var22, "GET_CONNECT_FAILD");
	} 


        System.out.println("AbstractSdncOperationTask.httpGet end!");
        return result;
    }

}
