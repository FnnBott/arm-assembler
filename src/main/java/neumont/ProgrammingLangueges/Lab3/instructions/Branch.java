package neumont.ProgrammingLangueges.Lab3.instructions;

public class Branch extends Instruction{

    
public byte[] branch(String[] instruction) {
    int cond = getConditionCode(instruction[1]);
    int opcode = 0b1010;
    int offset;

    if(cond != 0b1110){
        offset = calculateOffset(instruction[2]);
    }else{
        offset = calculateOffset(instruction[1]);
    }

    

    int machineCode = (cond << 28) | (opcode << 24) | (offset & 0x00FFFFFF);
    System.out.printf("0x%08X\n", machineCode);

    return toLittleEndian(machineCode);
}

private int calculateOffset(String operand) {
    int byteOffset = Integer.parseInt(operand);

    int wordOffset = byteOffset >> 2;

    return wordOffset & 0x00FFFFFF; // B -16 & B NE -3

}

private byte[] toLittleEndian(int value) {
    return new byte[] {
        (byte) (value),
        (byte) (value >> 8),
        (byte) (value >> 16),
        (byte) (value >> 24)
    };
}

}