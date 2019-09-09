package com.github.xjjdog.seckill.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesToBean {

    static List<String> reflectPackages = new ArrayList<>();

    static {
        reflectPackages.add("com.github.xjjdog.seckill");
        reflectPackages.add("redis.clients.jedis");
    }

    private static boolean match(String str) {
        for (String s : reflectPackages) {
            if (str.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从propertis到bean
     *
     * @param propsPrefix
     * @param properties
     * @param instance
     * @param <T>
     * @throws Exception
     */
    public static <T> void toBean(String propsPrefix, Properties properties, T instance) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass());
        for (PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {
            Class t = desc.getPropertyType();
            if (match(t.getName())) {
                String nextPrefix = propsPrefix + desc.getName() + ".";
                Object nextLevel = t.newInstance();
                toBean(nextPrefix, properties, nextLevel);
                Method method = desc.getWriteMethod();
                try {
                    method.invoke(instance, nextLevel);
                } catch (Exception ex) {
                    //noop ignore
                }
            } else {
                final String name = desc.getName();
                final Object value = properties.get(propsPrefix + name);
                if (null != value && !"".equals(value)) {
                    Method method = desc.getWriteMethod();
                    try {
                        method.invoke(instance, value);
                    } catch (Exception ex) {
                        //noop , ignore
                    }
                }
            }
        }
    }
}
