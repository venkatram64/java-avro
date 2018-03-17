package com.venkat.avro.service;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;

public class ReflectionCustomerEx {
    public static void main(String[] args){
        Schema schema = ReflectData.get().getSchema(ReflectionCustomer.class);
        System.out.println("schema = " + schema.toString(true));
        try{
            System.out.println("writing customer-reflect.avro");
            File file = new File("customer-reflect.avro");
            DatumWriter<ReflectionCustomer> writer = new ReflectDatumWriter<>(ReflectionCustomer.class);
            DataFileWriter<ReflectionCustomer> out = new DataFileWriter<>(writer)
                                    .setCodec(CodecFactory.deflateCodec(9))
                                    .create(schema, file);
            out.append(new ReflectionCustomer("Venkatram", "Veerareddy", "Venkanna"));
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //read from an avro into our reflected class
        try{
            System.out.println("reading customer-reflect.avro");
            File file = new File("customer-reflect.avro");
            DatumReader<ReflectionCustomer> reader = new ReflectDatumReader<>(ReflectionCustomer.class);
            DataFileReader<ReflectionCustomer> in = new DataFileReader<>(file, reader);
            for(ReflectionCustomer customer : in){
                System.out.println(customer.fullName());
            }

            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
