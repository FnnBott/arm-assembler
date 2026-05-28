package neumont.ProgrammingLangueges.Lab3.instructions;

import neumont.ProgrammingLangueges.Lab3.controller.ParsedInstruction;

public class SingleDataTransfer extends Instruction {
    
    public int ldr(ParsedInstruction instruction) { 
        return encodeLoadStore(instruction, 1); 
    }

    public int str(ParsedInstruction instruction) { 
        return encodeLoadStore(instruction, 0); 
    }


    private int encodeLoadStore(ParsedInstruction instr, int loadBit) {
    int cond = instr.conditionCode();
    int rd   = getRegister(instr.operands[0]);
    int L    = loadBit;
    int I = 0, B = 0;

    boolean writeback = false;
    boolean postIndex = false;
    for (int i = 0; i < instr.operands.length; i++) {
        if (instr.operands[i].contains("!")) {
            instr.operands[i] = instr.operands[i].replace("!", "");
            writeback = true;
        }
        if (i == 1 && instr.operands[i].contains("]")) {
            instr.operands[i] = instr.operands[i].replace("]", "");
            postIndex = true;
        }
    }

    int rn = getRegister(instr.operands[1]);

    int P, U, W, offset;

    if (instr.operands.length > 2) {
        int raw = parseImmediate(instr.operands[2]);
        U      = raw >= 0 ? 1 : 0;
        offset = Math.abs(raw) & 0xFFF;

        if (postIndex) {
            P = 0;
            W = 1;   
        } else {
            P = 1;
            W = writeback ? 1 : 0;
        }
    } else {
        P = 1;
        U = 1;
        W = 0;
        offset = 0;
    }

    int machineCode =
        (cond   << 28) |
        (0b01   << 26) |
        (I      << 25) |
        (P      << 24) |
        (U      << 23) |
        (B      << 22) |
        (W      << 21) |
        (L      << 20) |
        (rn     << 16) |
        (rd     << 12) |
        offset;

    System.out.printf("0x%08X%n", machineCode);
    return machineCode;
}
}