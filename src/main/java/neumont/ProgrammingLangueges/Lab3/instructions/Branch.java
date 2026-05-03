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

    return toLittleEndian(machineCode);
}

private int calculateOffset(String operand) {
    int instructionOffset = Integer.parseInt(operand); // B -16 

    return instructionOffset; 
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