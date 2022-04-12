package com.example.warehouseapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class Data {
    LinkedList<Bay> bayList = new LinkedList<>();
    LinkedList<Bay> roomList = new LinkedList<>();
    LinkedList<Bay> finishedList = new LinkedList<>();
    LinkedList<Bay> FilteredList = new LinkedList<>();
    String serverIP = "192.168.29.49";
    Data() throws IOException {
        String str;
        Socket s;
        PrintWriter pw;
        InputStreamReader in;
        BufferedReader br;
        /*
        while(true){
            str = scan.nextLine();
            s = new Socket("192.168.1.5", 4999);
            pw = new PrintWriter(s.getOutputStream());
            pw.println(str);
            pw.flush();
            in = new InputStreamReader(s.getInputStream());
            br = new BufferedReader(in);
            str = br.readLine();
            System.out.println(str);
        }
        */
        s = new Socket(serverIP, 4999);
        pw = new PrintWriter(s.getOutputStream());
        pw.println("1 0");
        pw.flush();
        in = new InputStreamReader(s.getInputStream());
        br = new BufferedReader(in);
        System.out.println("Data received From server");
        System.out.println("Data sorting starting");
        while((str = br.readLine()) != null) {
            String aisle;
            int bay;
            int job;
            int bin;
            String time;
            String[] data = str.split("\\s");
            if(data.length == 5) {

                aisle = data[0];
                bay = Integer.parseInt(data[1]);
                job = Integer.parseInt(data[2]);
                bin = Integer.parseInt(data[3]);
                time = data[4];
                Bay temp = new Bay(aisle, bay, job, bin, time);
                bayList.add(temp);

            }else if(data.length == 4){
                aisle = data[0];
                bay = Integer.parseInt(data[1]);
                job = Integer.parseInt(data[2]);
                time = data[3];
                Bay temp = new Bay(aisle,bay,job,time);
                finishedList.add(temp);

            }else if(data.length == 3){

                job = Integer.parseInt(data[0]);
                bin = Integer.parseInt(data[1]);
                time = data[2];
                Bay temp = new Bay (job, bin, time);
                roomList.add(temp);
            }


        }
        br.close();
        System.out.println("Worked!");
    }


}
