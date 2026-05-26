package neumont.ProgrammingLangueges.Lab3.instructions;

import java.util.HashMap;

import neumont.ProgrammingLangueges.Lab3.controller.ParsedInstruction;


public class Branch extends Instruction{
    public int branch(ParsedInstruction instruction, int currentAddress, HashMap<String, Integer> labelTable) {
        int cond = instruction.conditionCode();
        int L    = instruction.mnemonic.equals("BL") ? 1 : 0;

        if (instruction.mnemonic.equals("BX")) {
            int rm = getRegister(instruction.operands[0]);
            return (cond << 28) | 0x012FFF10 | rm;
        }

        String target = instruction.operands[0];
        int offset = labelTable.containsKey(target) ? labelTable.get(target) - currentAddress - 2 : Integer.parseInt(target);

        int machineCode = 
        (cond << 28) |
        (0b101 << 25) |
        (L << 24) |
        (offset & 0x00FFFFFF);

        System.out.printf("0x%08X\n", machineCode);

        return machineCode;
    }
}

