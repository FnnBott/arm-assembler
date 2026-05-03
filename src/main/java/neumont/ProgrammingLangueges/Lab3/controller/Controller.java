package neumont.ProgrammingLangueges.Lab3.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import neumont.ProgrammingLangueges.Lab3.instructions.Branch;

public class Controller {

    private ArrayList<String[]> assemblyLines = new ArrayList<>();
    private ArrayList<byte[]> machineCode = new ArrayList<>();

    public void readFile() {
        File assemblyFile = new File("BranchText.txt");

        try (Scanner reader = new Scanner(assemblyFile)) {
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                assemblyLines.add(trimString(data));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    private String[] trimString(String stringToTrim) {
        stringToTrim = stringToTrim.replace(",", "");
        return stringToTrim.split(" ");
    }

    public void assemble() {
        for (int i = 0; i < assemblyLines.size(); i++) {
            instructionSender(assemblyLines.get(i), i * 4);
        }
    }

    private void instructionSender(String[] instructionArray, int currentAddress) {
        switch (instructionArray[0]) {
            case "B" -> {
                Branch branch = new Branch();

                // For now: fake target example (you will replace with label resolution later)
                

                byte[] result = branch.branch(instructionArray);

                machineCode.add(result);
            }

            default -> {
                throw new IllegalArgumentException("Unknown instruction: " + instructionArray[0]);
            }
        }
    }

    public ArrayList<byte[]> getMachineCode() {
        return machineCode;
    }
}