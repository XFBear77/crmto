package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {
	
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object a(ProceedingJoinPoint pjp) throws Throwable {
		//��¼��ʼʱ��
		long start = System.currentTimeMillis();
		//ִ�з���,��ȡ����ֵ
		Object result = pjp.proceed();
		
		//��¼����ʱ��
		long end = System.currentTimeMillis();
		//�����ʱ
		System.err.println("��ʱ"+(end-start)+"���롣");
		
		//��������֮��ķ����ķ���ֵ
		return result;
	}
}
