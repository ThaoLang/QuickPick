//package com.example.quickpick.services.Notification;
//
//import com.example.quickpick.api_structure.GetDirectionNotification;
//import com.example.quickpick.constants.NotificationTypes;
//import com.example.quickpick.my_intefaces.NotificationConverterStrategy;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//public class GetBookingInfoNotificationConverterStrategy implements NotificationConverterStrategy
//{
//
//    private final String notificationType = NotificationTypes.GET_BOOKING_INFO_REQUEST;
//
//    @Override
//    public byte[] fromObjectToBytes(Object object) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        GetBooking notification;
//        ObjectOutputStream oos;
//        byte[] result = null;
//        try
//        {
//            oos = new ObjectOutputStream(baos);
//            notification = (GetDirectionNotification) object;
//            oos.writeObject(notification);
//            result = baos.toByteArray();
//            oos.close();
//            baos.close();
//        }
//        catch (IOException ioex)
//        {
//            return null;
//        }
//
//        return result;
//    }
//
//    @Override
//    public Object fromBytesToObject(byte[] bytes) {
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois;
//        GetDirectionNotification notification;
//        try
//        {
//            ois = new ObjectInputStream(bais);
//            notification = (GetDirectionNotification) ois.readObject();
//        }
//        catch (Exception ex)
//        {
//            return null;
//        }
//        return notification;
//    }
//
//    @Override
//    public String getNotificationType() {
//        return notificationType;
//    }
//}
