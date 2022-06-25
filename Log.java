package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Log {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a"); // lowercase hh for 12-hour format, a for am/pm
    LocalDateTime now = LocalDateTime.now(); //<-- getting local time
     FileOutputStream output; //Creates a file output stream to write to the file
     PrintWriter writer;

     public void createOutput() {
        try {
            File file = new File("capstone-1/Log.txt");
            output = new FileOutputStream(file); //<-- making sure file is found
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
      }

    public void log(String activity, File file) { //<-- added file since file in main is different from file found in test

            if (writer == null) {       // if writer is null create new printWriter
                try {
                    writer = new PrintWriter(file); //<-- creating instance of PrintWriter
                    writer.println(dtf.format(now) + " " + activity); //<--printing time, then activity in Log.txt

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                writer.println(dtf.format(now) + " " + activity);
            }
            // using.flush, the file can be kept open and the log message written with each call.
            writer.flush(); //<-- using .flush to append when method called

    }
}
