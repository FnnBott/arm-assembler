package neumont.ProgrammingLangueges.Lab3.controller;

import java.util.Arrays;
import java.util.Set;

public class ParsedInstruction {

    public final String   label;      
    public final String   mnemonic;  
    public final String   condition;  
    public final String[] operands;

    private static final Set<String> CONDITION_CODES = 
    Set.of(
        "EQ","NE","CS","CC","MI","PL","VS","VC","HI","LS","GE","LT","GT","LE","AL" 
    );

    public ParsedInstruction(String line) {
        String[] tokens = line.trim().split("\\s+");
        int start = 0;

        if (tokens[0].startsWith("$")) {
            label = tokens[0].substring(1);
            start = 1;
        } else {
            label = null;
        }

        String full = tokens[start].toUpperCase();
        String peeled = peelCondition(full);
        if (peeled != null) {
            mnemonic  = full.substring(0, full.length() - 2);
            condition = peeled;
        } else {
            mnemonic  = full;
            condition = "AL";
        }

        operands = Arrays.copyOfRange(tokens, start + 1, tokens.length);

        for (int i = 0; i < operands.length; i++) {
            operands[i] = operands[i].replace("[", "").replace("]", "");
        }
    }

    private String peelCondition(String full) {
        if (full.length() > 2) {
            String suffix = full.substring(full.length() - 2);
            if (CONDITION_CODES.contains(suffix)) return suffix;
        }
        return null;
    }

    public int conditionCode() {
        return switch (condition) {
            case "EQ" -> 0b0000; case "NE" -> 0b0001;
            case "CS" -> 0b0010; case "CC" -> 0b0011;
            case "MI" -> 0b0100; case "PL" -> 0b0101;
            case "VS" -> 0b0110; case "VC" -> 0b0111;
            case "HI" -> 0b1000; case "LS" -> 0b1001;
            case "GE" -> 0b1010; case "LT" -> 0b1011;
            case "GT" -> 0b1100; case "LE" -> 0b1101;
            default   -> 0b1110;
        };
    }
}