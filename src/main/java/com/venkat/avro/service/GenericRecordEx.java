package com.venkat.avro.service;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.*;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordEx {
    /*
    {
            "type":"record",
            "namespace":"com.venkat.avro.service",
            "name":"Customer",
            "doc": "Avro Schema for our Customer",
            "fields":[
                {"name": "first_name","type": "string","doc":""} ,
                {"name": "last_name","type": "string", "doc":""} ,
                {"name": "age","type": "int", "doc":""} ,
                {"name": "height","type": "float", "doc":""} ,
                {"name": "weight","type": "float", "doc":""} ,
                {"name": "automated_email","type": "boolean","default":true, "doc":""}
            ]
        }
     */
    public void schemaReader(){
        //step 0: defne schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "            \"type\":\"record\",\n" +
                "            \"namespace\":\"com.venkat.avro.service\",\n" +
                "            \"name\":\"Customer\",\n" +
                "            \"doc\": \"Avro Schema for our Customer\",\n" +
                "            \"fields\":[\n" +
                "                {\"name\": \"first_name\",\"type\": \"string\",\"doc\":\"\"} ,\n" +
                "                {\"name\": \"last_name\",\"type\": \"string\", \"doc\":\"\"} ,\n" +
                "                {\"name\": \"age\",\"type\": \"int\", \"doc\":\"\"} ,\n" +
                "                {\"name\": \"height\",\"type\": \"float\", \"doc\":\"\"} ,\n" +
                "                {\"name\": \"weight\",\"type\": \"float\", \"doc\":\"\"} ,\n" +
                "                {\"name\": \"automated_email\",\"type\": \"boolean\",\"default\":true, \"doc\":\"\"}\n" +
                "            ]\n" +
                "        }");
        //step 1: create a generic record
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name","Venkatram");
        customerBuilder.set("last_name","Veerareddy");
        customerBuilder.set("age",51.4f);
        customerBuilder.set("height",162.4f);
        customerBuilder.set("weight",71.4f);
        customerBuilder.set("automated_email",false);
        GenericData.Record customer = customerBuilder.build();
        System.out.println(customer);

        GenericRecordBuilder customerBuilder2 = new GenericRecordBuilder(schema);
        customerBuilder2.set("first_name","Venkatram");
        customerBuilder2.set("last_name","Veerareddy");
        customerBuilder2.set("age",51.4f);
        customerBuilder2.set("height",162.4f);
        customerBuilder2.set("weight",71.4f);
        GenericData.Record customer2 = customerBuilder2.build();
        System.out.println(customer2);

        //step 2: write that generic record to a file
        final DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        try(DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(writer)){
            dataFileWriter.create(customer.getSchema(), new File("customer-generic.avro"));
            dataFileWriter.append(customer);
            System.out.println("Written customer-generic.avro");

        }catch (IOException e){
            System.out.println("Could not write a file.");
        }
        //step 3: read a generic record from a file
        final File file = new File("customer-generic.avro");
        final DatumReader<GenericRecord> reader =  new GenericDatumReader<>();
        GenericRecord customerRead;
        try(DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file,reader)){
            customerRead = dataFileReader.next();
            System.out.println("Successfully read avro file");
            System.out.println(customerRead.toString());

            //step 4: interpret as a generic record
            System.out.println("First name "+ customerRead.get("first_name"));
            System.out.println("Last name "+ customerRead.get("last_name"));
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        GenericRecordEx genericRecord = new GenericRecordEx();
        genericRecord.schemaReader();
    }
}
