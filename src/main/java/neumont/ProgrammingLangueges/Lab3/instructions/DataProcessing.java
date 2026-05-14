
package neumont.ProgrammingLangueges.Lab3.instructions;

public class DataProcessing extends Instruction {

    /*
        convert instruction to binary
            read first section of the instruction e.g. MOVT, MOVW
            get the condition
            get the opCode
    */
    
    public int movw(String[] instruction) {
        return encodeMoveWide(instruction, 0); // 0 = MOVW
    }

    public int movt(String[] instruction) {
        return encodeMoveWide(instruction, 1); // 1 = MOVT
    }

    public int nonSBit(String[] instruction){
        return encodeDataProcessing(instruction, 0);
    }

    public int subs(String[] instruction){
        return encodeDataProcessing(instruction, 1);
    }


    private int encodeMoveWide(String[] instruction, int isTop) {
        int cond = getConditionCode(instruction[1]);

        int rd;
        int imm16;

        if (cond != 0b1110) {
            rd = getRegister(instruction[2]);
            imm16 = parseImmediate(instruction[3]) & 0xFFFF;
        } else {
            rd = getRegister(instruction[1]);
            imm16 = parseImmediate(instruction[2]) & 0xFFFF;
        }

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

    private int encodeDataProcessing(String[] instruction, int subS){
        String mnemonic = instruction[0].endsWith("S") ? instruction[0].substring(0, instruction[0].length() - 1) : instruction[0];
        int cond = getConditionCode(instruction[1]);
        int opcode = getOpCode(mnemonic);
        int S = subS;
        int rn;
        int rd;
        int operand = encodeImmediate(parseImmediate(instruction[instruction.length - 1]));


        if (cond != 0b1110) {
            rd = getRegister(instruction[2]);
            rn = getRegister(instruction[3]);
        } else {
            rd = getRegister(instruction[1]);
            rn = getRegister(instruction[2]);
        }

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

    private int encodeImmediate(int value) {
    for (int rot = 0; rot < 16; rot++) {

        int rotated = Integer.rotateRight(value, rot * 2);

        if ((rotated & 0xFFFFFF00) == 0) {
            return (rot << 8) | (rotated & 0xFF);
        }
    }
    throw new IllegalArgumentException(
        "Immediate 0x" + Integer.toHexString(value) + " cannot be encoded");
}
}