package com.contacts.aspect;

import com.contacts.db.HibernateUtil;
import com.contacts.model.UserSession;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Alexander Vashurin 07.02.2017
 */
@Aspect
public class LoginAspect {
    @Pointcut("execution(String com.contacts.WebController.*(..))")
    public void serviceMethod() { }

    @Around("serviceMethod()")
    public Object endpointCall(ProceedingJoinPoint thisJoinPoint) {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();
        String invocationClass = thisJoinPoint.getSignature().getDeclaringType().getSimpleName();

        Object result = null;

        if (methodName.equals("login") || methodName.equals("registration")) {
            try {
                result = thisJoinPoint.proceed();
            } catch (Throwable e) {
            }
            return result;
        }



        HttpServletRequest request = null;
        HttpServletResponse response = null;

        Object[] requests = Arrays.asList(methodArgs).stream().filter(arg -> arg instanceof HttpServletRequest).toArray();
        Object[] responses = Arrays.asList(methodArgs).stream().filter(arg -> arg instanceof HttpServletResponse).toArray();
        if (requests.length > 0)
            request = (HttpServletRequest)requests[0];
        if (responses.length > 0)
            response = (HttpServletResponse)responses[0];

        //check login here
        HttpSession session = null;
        if (request != null) {
            session = request.getSession(true);
            //check if logged in
            Integer loggedIn = (Integer) session.getAttribute("loggedIn");
            Session dbSession = HibernateUtil.getSessionFactory().openSession();
            Query query = dbSession.createQuery("from UserSession where sessionId = :id");
            query.setParameter("id", loggedIn);
            UserSession userSession = (UserSession) query.uniqueResult();
            if (userSession != null) {
                try { result = thisJoinPoint.proceed();} catch (Throwable e) {}
            } else {
                redirectToLogin(response);
            }

        }

        return result;
    }

    private static void redirectToLogin(HttpServletResponse response) {
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {}
    }
}
