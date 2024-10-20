package ru.clevertec.starter.bpp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import ru.clevertec.starter.annotation.SessionInjector;
import ru.clevertec.starter.exception.LoginBlacklistedException;
import ru.clevertec.starter.model.Session;
import ru.clevertec.starter.model.SessionRequest;
import ru.clevertec.starter.service.SessionListener;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@RequiredArgsConstructor
public class SessionInjectorBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    public static final String LOGIN_S = "Login: %s";
    private final SessionListener sessionListener;
    private final Set<String> blackList;
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .forEach(this::addNewRowToBlackList);
        return bean;
    }

    private void addNewRowToBlackList(Method method) {
        SessionInjector annotation = method.getAnnotation(SessionInjector.class);
        if (annotation != null) {
            Arrays.stream(annotation.blackList())
                    .forEach(aClass -> blackList.addAll(beanFactory.getBean(aClass).getBlackList()));
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isNotAnnotatedBean(bean)) return bean;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            SessionInjector annotation = method.getAnnotation(SessionInjector.class);
            if (annotation != null) {
                Arrays.stream(args)
                        .filter(argument -> SessionRequest.class.isAssignableFrom(argument.getClass()))
                        .forEach(argument -> injectSession((SessionRequest) argument));
            }
            return proxy.invokeSuper(obj, args);
        });
        return enhancer.create();
    }

    private void injectSession(SessionRequest argument) {
        String login = argument.getLogin();
        if (blackList.contains(login))
            throw new LoginBlacklistedException(String.format(LOGIN_S, login));
        Session session = sessionListener.getSession(login);
        argument.setSession(session);
    }

    private boolean isNotAnnotatedBean(Object bean) {
        return Arrays.stream(bean.getClass().getDeclaredMethods())
                .allMatch(method -> method.getAnnotation(SessionInjector.class) == null);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
