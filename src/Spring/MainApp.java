package Spring;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by liyangbd on 2018/9/19.
 */
public class MainApp {
	public static void main(String[] args) {
		//ApplicationContext 方式加载xml配置 推荐 ClassPathXmlApplicationContext 相对路径
		ApplicationContext context = new ClassPathXmlApplicationContext("xml/beans.xml");
		HelloWorld objA = (HelloWorld) context.getBean("helloworld1");
		objA.setMessage("I am objA");
		objA.getMessage();

		HelloWorld objB = (HelloWorld) context.getBean("helloworld1");
		objB.getMessage();

		HelloWorld objC = (HelloWorld) context.getBean("helloworld2");
		objC.setMessage("I am objA");
		objC.getMessage();

		HelloWorld objD = (HelloWorld) context.getBean("helloworld2");
		objD.getMessage();

		System.out.println("========================================");
		//XmlBeanFactory 方式加载xml配置
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("xml/beans.xml"));
		HelloWorld xmlObj = (HelloWorld) context.getBean("helloworld");
		xmlObj.getMessage();
		//ApplicationContext 通过FileSystemXmlApplicationContext加载配置文件 绝对路径
		ApplicationContext filecontext = new FileSystemXmlApplicationContext("D:\\IdeaProjects\\B\\src\\xml\\beans.xml");
		HelloWorld fileobj = (HelloWorld) context.getBean("helloworld");
		fileobj.getMessage();

		System.out.println("========================================");

		AbstractApplicationContext contextAbs = new ClassPathXmlApplicationContext("xml/beans.xml");
		HelloWorld obj = (HelloWorld) contextAbs.getBean("helloworldabs");

		obj.getMessage();
		contextAbs.registerShutdownHook();

		System.out.println("========================================");


		HelloWorld objabs = (HelloWorld) contextAbs.getBean("helloworldabsdef");

		objabs.getMessage();
		contextAbs.registerShutdownHook();

	}
}
