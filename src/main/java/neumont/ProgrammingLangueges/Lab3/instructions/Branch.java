package neumont.ProgrammingLangueges.Lab3.instructions;

import java.util.HashMap;

import neumont.ProgrammingLangueges.Lab3.controller.ParsedInstruction;


public class Branch extends Instruction{
    public int branch(ParsedInstruction instr, int currentAddress, HashMap<String, Integer> labelTable) {
        int cond = instr.conditionCode();
        int L    = instr.mnemonic.equals("BL") ? 1 : 0;

        if (instr.mnemonic.equals("BX")) {
            int rm = getRegister(instr.operands[0]);
            return (cond << 28) | 0x012FFF10 | rm;
        }

        String target = instr.operands[0];
        int offset = labelTable.containsKey(target) ? labelTable.get(target) - currentAddress : Integer.parseInt(target);

        return (cond << 28) | (0b101 << 25) | (L << 24) | (offset & 0x00FFFFFF);
    }
}

