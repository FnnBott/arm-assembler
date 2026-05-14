
package neumont.ProgrammingLangueges.Lab3.instructions;

import neumont.ProgrammingLangueges.Lab3.controller.ParsedInstruction;

public class DataProcessing extends Instruction {

    /*
        convert instruction to binary
            read first section of the instruction e.g. MOVT, MOVW
            get the condition
            get the opCode
    */
    
    public int movw(ParsedInstruction instruction) {
        return encodeMoveWide(instruction, 0); // 0 = MOVW
    }

    public int movt(ParsedInstruction instruction) {
        return encodeMoveWide(instruction, 1); // 1 = MOVT
    }

    public int nonSBit(ParsedInstruction instruction){
        return encodeDataProcessing(instruction, 0);
    }

    public int subs(ParsedInstruction instruction){
        return encodeDataProcessing(instruction, 1);
    }


    private int encodeMoveWide(ParsedInstruction instruction, int isTop) {
        int cond  = instruction.conditionCode();
        int rd    = getRegister(instruction.operands[0]);
        int imm16 = parseImmediate(instruction.operands[1]) & 0xFFFF;

        // Split imm16
        int imm4 = (imm16 >> 12) & 0xF;
        int imm12 = imm16 & 0xFFF;

        int machineCode = 
            (cond<<28) |
            (0b00110<<23) |
            (isTop<<22) | 
            (imm4<<16) | 
            (rd<<12) | 
            imm12;

        System.out.printf("0x%08X\n", machineCode);

        return machineCode;
    }

    private int encodeDataProcessing(ParsedInstruction instruction, int subS){
        
        int cond = instruction.conditionCode();
        int opcode = getOpCode(instruction.mnemonic);
        int S = subS;
        int rn = getRegister(instruction.operands[0]);
        int rd = getRegister(instruction.operands[1]);
        int operand = parseImmediate(instruction.operands[2]);


        int machineCode =
        (cond << 28) |
        (0b00 << 26) |
        (0b1 << 25) |
        (opcode << 21) |
        (S << 20) |
        (rn << 16) |
        (rd << 12) |
        operand;

        System.out.printf("0x%08X\n", machineCode);

        return machineCode;
    }
}