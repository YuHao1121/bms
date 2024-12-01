package org.example;

import org.junit.jupiter.api.Test;

/**
 * ClassName:ThreadLocal
 * Package:org.example
 * Description:
 *
 * @Author: 唐玉亮
 * @Craete:2024/11/2 - 13:06
 */
public class ThreadLocalTest {

    @Test
    public void testThreadLocalSetAndGet() {

        //提供一个threadlocal对象
        ThreadLocal tl = new ThreadLocal();


        //开启两个线程
        new Thread(()->{
            tl.set("xiaoyan");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"蓝色").start();

        new Thread(()->{
            tl.set("yaochen");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"绿色").start();
    }

}