package neumont.ProgrammingLangueges.Lab3.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import neumont.ProgrammingLangueges.Lab3.instructions.Branch;
import neumont.ProgrammingLangueges.Lab3.instructions.DataProcessing;
import neumont.ProgrammingLangueges.Lab3.instructions.SingleDataTransfer;

public class Controller {

    private ArrayList<String[]> assemblyLines = new ArrayList<>();
    private ArrayList<Integer> instructionList = new ArrayList<>();

    private final Branch branch = new Branch();
    private final DataProcessing dataProcessing = new DataProcessing();
    private final SingleDataTransfer singleDataTransfer = new SingleDataTransfer();

    public void readFile() {

        File assemblyFile = new File("Lab3Assembly.txt");

        try (Scanner reader = new Scanner(assemblyFile)) {

            while (reader.hasNextLine()) {

                String line = reader.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                assemblyLines.add(trimString(line));
            }

        } catch (FileNotFoundException e) {

            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    private String[] trimString(String line) {
        return line.trim().split("\\s+");
    }

    public void assemble() {

        for (String[] instruction : assemblyLines) {

            int machineCode = instructionSender(instruction);

            instructionList.add(machineCode);
        }
    }

    private int instructionSender(String[] instructionArray) {

        String instruction = instructionArray[0].toUpperCase();

        switch (instruction) {

            case "B", "BL", "BX" -> {
                return branch.branch(instructionArray);
            }
        }

        switch (instruction) {

            case "MOVW" -> {
                return dataProcessing.movw(instructionArray);
            }
            case "MOVT" -> {
                return dataProcessing.movt(instructionArray);
            }
            case "ADD", "SUB", "AND", "ORR" -> {
                return dataProcessing.nonSBit(instructionArray);
            }
            case "SUBS" -> {
                return dataProcessing.subs(instructionArray);
            }
        }

        switch (instruction) {

            case "LDR" -> {
                return singleDataTransfer.ldr(instructionArray);
            }

            case "STR" -> {
                return singleDataTransfer.str(instructionArray);
            }
        }

        throw new IllegalArgumentException(
                "Unknown instruction: " + instruction);
    }

    public ArrayList<Integer> getInstructionList() {
        return instructionList;
    }

    protected  byte[] toLittleEndian(int value) {
        return new byte[] {
            (byte) (value),
            (byte) (value >> 8),
            (byte) (value >> 16),
            (byte) (value >> 24)
        };
    }

    public void writeKernelImage() {

    try (FileOutputStream out =
                 new FileOutputStream("kernel7.img")) {

        for (int instruction : instructionList) {

            out.write(toLittleEndian(instruction));
        }

        System.out.println("it didnt break");

    } catch (IOException e) {

        System.out.println("It Broke");
        e.printStackTrace();
    }
}
}