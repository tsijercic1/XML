package com.github.tsijercic1;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;


public class Main {
    public static boolean hasSetterForField(Class<?> type, Field field) {
        Method[] methods = type.getDeclaredMethods();
        String firstLetter = String.valueOf(field.getName().charAt(0)).toUpperCase();
        String target = "set" + firstLetter + field.getName().substring(1);

        for (Method method : methods) {
            if (method.getName().equals(target)) {
                System.out.println("Access modifier: "+Modifier.toString(method.getModifiers()));
                return true;
            }
        }
        return false;
    }

    public static boolean hasGetterForField(Class<?> type, Field field) {
        Method[] methods = type.getDeclaredMethods();
        String firstLetter = String.valueOf(field.getName().charAt(0)).toUpperCase();
        String target = "get" + firstLetter + field.getName().substring(1);
        String targetReplacement = "is" + firstLetter + field.getName().substring(1);
        for (Method method : methods) {
            if (method.getName().equals(target) || method.getName().equals(targetReplacement)) {
                System.out.println("Access modifier: "+Modifier.toString(method.getModifiers()));
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Person person = new Person();
        person.setName("Name");
        person.setSurname("Surname");
        person.setAge(22);
        person.setBirthday(LocalDate.of(1998, 7, 22));
        Class<?> personClass = person.getClass();
        for (Field field:personClass.getDeclaredFields()) {
            System.out.println(field);
            System.out.println(field.getAnnotatedType());
            System.out.println("Has setter for "+field.getName() + " = " + hasSetterForField(personClass,field));
            System.out.println("Has getter for "+field.getName() + " = " + hasGetterForField(personClass,field));
        }
        Constructor<?>[] constructors = personClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
            System.out.println(constructor.getParameterCount());
        }
        Tip tip = Tip.DOBAR;
        tip.ordinal();
    }
}
//  boolean, byte, short, int, long, char, float, and double.