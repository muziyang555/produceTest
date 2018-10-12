package Spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by liyangbd on 2018/9/26.
 */
public class InitHelloWorld implements BeanPostProcessor {
//	public Object postProcessorBeforeInitialization(Object bean,String beanname) throws BeanExpressionException{
//		System.out.println("Before 	:"+beanname);
//
//		return bean;
//
//	}
//	public Object postProcessorAfterInitialization(Object bean,String beanname) throws BeanExpressionException{
//		System.out.println("After 	:"+beanname);
//
//		return bean;
//
//	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Before 	:"+beanName);

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("After 	:"+beanName);

		return bean;
	}
}
