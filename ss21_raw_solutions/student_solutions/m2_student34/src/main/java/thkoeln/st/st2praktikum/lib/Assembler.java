package thkoeln.st.st2praktikum.lib;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.util.*;

public class Assembler {

    private static final List<Class<?>> BEAN_ANNOTATIONS =
            Arrays.asList(Component.class, Repository.class, Service.class);

    private final Map<Class<?>, Object> beans;

    public Assembler() {
        this.beans = new HashMap<>();
    }

    /**
     * <p>Will return an instance of bean.</p>
     *
     * <p>bean must be a concrete class. It is not possible to find an instance
     * matching a given interface.</p>
     *
     * @param bean
     * @param <T> Type of Bean
     * @return instance of bean
     */
    public <T> T getBean(Class<T> bean) {
        if (!this.isValidBeanClass(bean)) {
            throw new IllegalArgumentException(bean.getName() + " is no valid" +
                    " bean");
        }
        if (this.beans.containsKey(bean)) {
            return (T) this.beans.get(bean);
        }

        Constructor<?> constructor =
                Arrays.stream(bean.getConstructors())
                        .min(Comparator
                                .comparing(Constructor::getParameterCount))
                        .orElseThrow(() -> new RuntimeException(new IllegalClassFormatException()));

        var dependencies =
                Arrays.stream(constructor.getParameterTypes())
                        .map(this::getBean).toArray();

        try {
            T beanInstance = (T) constructor.newInstance(dependencies);
            beans.put(bean, beanInstance);
            return beanInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> boolean isValidBeanClass(Class<T> bean) {
        return Arrays.stream(bean.getAnnotations())
                .map(Annotation::annotationType)
                .anyMatch(it -> Assembler.BEAN_ANNOTATIONS.stream().anyMatch(
                        itValid -> itValid.isAssignableFrom(it)
                ));
    }
}
