package neumont.ProgrammingLangueges.Lab3.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import neumont.ProgrammingLangueges.Lab3.instructions.Branch;
import neumont.ProgrammingLangueges.Lab3.instructions.DataProcessing;
import neumont.ProgrammingLangueges.Lab3.instructions.SingleDataTransfer;

public class Controller {

    private ArrayList<ParsedInstruction> assemblyLines = new ArrayList<>();
    private ArrayList<Integer> instructionList = new ArrayList<>(); 

    private final Branch branch = new Branch();
    private final DataProcessing dataProcessing = new DataProcessing();
    private final SingleDataTransfer singleDataTransfer = new SingleDataTransfer();
    private HashMap<String, Integer> labelTable = new HashMap<>();

    public void readFile() {
    File assemblyFile = new File("Lab5Assembly.txt");

    try (Scanner reader = new Scanner(assemblyFile)) {

        while (reader.hasNextLine()) {
            String line = reader.nextLine().trim();

            if (line.isEmpty()) continue;

            assemblyLines.add(new ParsedInstruction(line)); 
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

    private String[] trimString(String line) {
        return line.trim().split("\\s+");
    }

    public void buildLabelTable() {
    int address = 0;
    for (ParsedInstruction instruction : assemblyLines) {
        if (instruction.label != null) labelTable.put(instruction.label, address);
        address++;
    }
}   

public void assemble() {
    int currentAddress = 0;
    for (ParsedInstruction instruction : assemblyLines) {
        instructionList.add(instructionSender(instruction, currentAddress));
        currentAddress++;
    }
}

    private int instructionSender(ParsedInstruction instruction, int currentAddress) {
    switch (instruction.mnemonic) {
        case "B", "BL", "BX" -> { return branch.branch(instruction, currentAddress, labelTable); }
        case "MOVW"           -> { return dataProcessing.movw(instruction); }
        case "MOVT"           -> { return dataProcessing.movt(instruction); }
        case "ADD", "SUB", "AND", "ORR" -> { return dataProcessing.nonSBit(instruction); }
        case "SUBS"           -> { return dataProcessing.subs(instruction); }
        case "LDR"            -> { return singleDataTransfer.ldr(instruction); }
        case "STR"            -> { return singleDataTransfer.str(instruction); }
        default -> throw new IllegalArgumentException("Unknown instruction: " + instruction.mnemonic);
    }
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