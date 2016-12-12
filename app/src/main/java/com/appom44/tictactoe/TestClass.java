package com.appom44.tictactoe;

/**
 * Created by swar8080 on 12/2/2016.
 */

public class TestClass {

    public static void main(String[] args) {
        myinterface m = foo();
        m.throwsomething();
    }

    public static myinterface foo() {

        final String x = "HELLO";

        myinterface mi = new myinterface() {

            private secondinterface si;

            @Override
            public void register(secondinterface i) {
                si = i;
            }

            @Override
            public void throwsomething()  {
                throw new RuntimeException(x);
            }
        };

        return mi;
    }


    interface myinterface{
        void register(secondinterface i);
        void throwsomething();
    }

    interface secondinterface{
        void doSomething2();
    }





}
