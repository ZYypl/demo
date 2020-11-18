package com.example.demo.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * com.example.demo.config
 *
 * @author ypl
 * @create 2020-11-13 15:04
 */
public class ByteRedisSerialize implements RedisSerializer {

    private static final byte[] EMP_BYTE=new byte[0];
    @Override
    public byte[] serialize(Object o) throws SerializationException {

        if(o==null){
            return EMP_BYTE;
        }else if(o instanceof byte[]){
            return (byte[])o;
        }else {
            ObjectOutputStream objectOutputStream=null;
            ByteArrayOutputStream arrayOutputStream =null;
            Object o1;
            try{
                arrayOutputStream=new ByteArrayOutputStream();
                objectOutputStream=new ObjectOutputStream(arrayOutputStream);
                objectOutputStream.writeObject(o);
                objectOutputStream.flush();
                byte[] bytes=arrayOutputStream.toByteArray();
                byte[] v=bytes;
                return v;
            }catch (Exception e){
                o1=null;
            }finally {
                if(objectOutputStream!=null
                ||arrayOutputStream!=null){
                    try {
                        objectOutputStream.close();
                        arrayOutputStream.close();
                    }catch (Exception e){}
                }
            }
            return (byte[])o1;

        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes==null) return null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            return  o;
        }catch (Exception e){

        }finally {
          //close;
        }
        return null;

        }
}
