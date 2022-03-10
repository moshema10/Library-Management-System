package com.example.student_module;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * It is a Student class.
 */
public class Student {
    private static Formatter requestFile;
    private static Scanner readFile;
    private static ArrayList<String> students = new ArrayList<>();
    private static ArrayList<RequestRecord> requests = new ArrayList<>();

    private String studentId;

    static {
        try (BufferedReader br = new BufferedReader(new FileReader("studentDB.txt"))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record);
                students.add(in.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens the requestDB text file for writing to the file
     */
    private void openFile()
    {

        try{
            FileWriter fw = new FileWriter("requestDB.txt", true);
            requestFile = new Formatter(fw);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to the Request file for students request on issuing book
     * @param bid Book ID
     */
    public void writeRequestFile(String bid) {
        try (BufferedReader br = new BufferedReader(new FileReader("requestDB.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("requestDB.txt", true))) {
            String record;
            int lastTicketID = 0;
            requests.clear();
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                lastTicketID = in.nextInt();
            }

            String str = String.format("%s|%s|%s%n", lastTicketID + 1, studentId, bid);
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It checks status of a books availability
     * @param bid Book ID
     * @return true if the book is available for issuing else false.
     */
    public boolean checkStatus(String bid)
    {
        try (BufferedReader br = new BufferedReader(new FileReader("requestDB.txt"))) {
            String record;
            requests.clear();
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                requests.add(new RequestRecord(in.nextInt(), in.next(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean found = false;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).BookID.equals(bid)
                    && requests.get(i).studentID.equals(studentId)) {
                found = true;
                break;
            }
        }
        return found;
     }

    /**
     * Validates Student ID.
     * @param id Student ID
     * @return true if found in the students array.
     */

    public static boolean validateID(String id)
    {
        boolean found = false;
        for (int i = 0; i < students.size(); i++) {
            if (id.equals(students.get(i))) {
                found = true;
                break;
            }
        }
        return found;
    }

    public String getStudentId() {
        return studentId;
    }


    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}


/**
 * Handles the students information who is requesting for issuing a book.
 */
class RequestRecord {
    int ticketID;
    String studentID;
    String BookID;

    RequestRecord(int id, String sid, String bid) {
        ticketID = id;
        studentID = sid;
        BookID = bid;
    }
}
