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
    int rd = getRegister(instr.operands[0]);

    String address = instr.operands[1].replace("[", "").replace("]", "");
    String[] parts = address.split(",");

    int rn = getRegister(parts[0]);
    int offset = parts.length > 1 ? parseImmediate(parts[1]) & 0xFFF : 0;

    int 
    I = 0, 
    P = 0, 
    U = 0, 
    B = 0, 
    W = 0, 
    L = loadBit;

    return 
        (cond << 28) |
        (0b01 << 26) |
        (I << 25) |
        (P << 24) |
        (U << 23) |
        (B << 22) |
        (W << 21) |
        (L << 20) | 
        (rn << 16) | 
        (rd << 12) | 
        offset;
}
}