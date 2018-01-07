package com.xyzlast.bookstore.aop;

import org.junit.Test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ReflectionTest {
    public interface Hello {
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    public class HelloImpl implements Hello {
        @Override
        public String sayHello(String name) {
            return "Hello " + name + "!!";
        }
        @Override
        public String sayHi(String name) {
            return "Hi " + name + "!!";
        }
        @Override
        public String sayThankYou(String name) {
            return "Thank you. " + name + "!!";
        }
    }

    public class UppercaseHandler implements InvocationHandler {
        private final Object target;

        public UppercaseHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String ret = (String) method.invoke(this.target, args);
            return ret.toUpperCase();
        }
    }

    @Test
    public void helloWorldReflection() throws Exception {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(),
            new Class[] { Hello.class},
            new UppercaseHandler(new HelloImpl()));

        String output = proxiedHello.sayHello("ykyoon");
        System.out.println("Output은 다음과 같습니다. : " + output);
        System.out.println("Proxy의 이름은 다음과 같습니다. : " + proxiedHello.getClass().getName());
    }

    @Test
    public void doDynamicReflection() throws Exception {
        String name = "ykyoon";
        Method lengthMethhod = String.class.getMethod("length");
        Object invokeResult = lengthMethhod.invoke(name);
        assertThat(invokeResult).isInstanceOf(Integer.class).isEqualTo(6);
    }

}
