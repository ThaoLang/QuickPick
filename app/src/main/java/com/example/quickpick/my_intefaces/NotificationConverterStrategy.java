package com.example.quickpick.my_intefaces;

public interface NotificationConverterStrategy
{
    byte[] fromObjectToBytes(Object object);
    Object fromBytesToObject(byte[] bytes);
    String getNotificationType();
}