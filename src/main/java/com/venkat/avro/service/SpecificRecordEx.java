package com.venkat.avro.service;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.File;
import java.io.IOException;

public class SpecificRecordEx {

    public void process(){
        //step 1: create specific record
        Customer.Builder newCustomer = Customer.newBuilder();
        newCustomer.setFirstName("Venkatram");
        newCustomer.setLastName("Veerareddy");
        newCustomer.setAge(51);
        newCustomer.setWeight(71.4f);
        newCustomer.setHeight(162.3f);
        newCustomer.setAutomatedEmail(false);
        Customer customer = newCustomer.build();
        System.out.println(customer.toString());

        //step 2: write to file
        DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);
        try(DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
            System.out.println("Successfully wrote customer-specific.avro");
        }catch(IOException e){
            e.printStackTrace();
        }

        //step 3: read from file
        final File file = new File("customer-specific.avro");
        final DatumReader<Customer> reader =  new SpecificDatumReader<>(Customer.class);
        Customer customerRead;
        try(DataFileReader<Customer> dataFileReader = new DataFileReader<>(file, reader)){
            customerRead = dataFileReader.next();
            System.out.println("Successfully read avro file");
            System.out.println(customerRead.toString());

            //step 4: interpret as a generic record
            System.out.println("First name "+ customerRead.getFirstName());
            System.out.println("Last name "+ customerRead.getLastName());
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SpecificRecordEx specificRecordEx = new SpecificRecordEx();
        specificRecordEx.process();
    }
}
