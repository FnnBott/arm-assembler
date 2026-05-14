package neumont.ProgrammingLangueges.Lab3.instructions;

public class Branch extends Instruction {

    public int branch(String[] instruction) {

        int cond = getConditionCode(instruction[1]);

        int offset;

        if (cond != 0b1110) {
            offset = calculateOffset(instruction[2]);
        } else {
            offset = calculateOffset(instruction[1]);
        }

        int machineCode =
                (cond << 28) |
                (0b1010 << 24) |
                (offset & 0x00FFFFFF);

        System.out.printf("0x%08X\n", machineCode);

        return machineCode;
    }

    private int calculateOffset(String operand) {
        int wordOffset = Integer.parseInt(operand);
        return wordOffset & 0x00FFFFFF;
    }
}