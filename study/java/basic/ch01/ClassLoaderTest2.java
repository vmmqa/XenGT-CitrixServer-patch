public class ClassLoaderTest2
{
    public static void main(String[] args)
	    {
		        System.out.println("BootstrapClassLoader="+System.getProperty("sun.boot.class.path"));
		        System.out.println("BootstrapClassLoader2="+System.getProperty("sun.mic.boot.class"));
		        System.out.println("ExtClassLoader="+System.getProperty("java.ext.dirs"));
		        System.out.println("AppClassLoader="+System.getProperty("java.class.path"));
		        System.out.println("mso="+System.getProperty("mso.config.path"));
		}
}
