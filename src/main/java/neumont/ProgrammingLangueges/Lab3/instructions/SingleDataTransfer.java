package neumont.ProgrammingLangueges.Lab3.instructions;

public class SingleDataTransfer extends Instruction {
    
    public int ldr(String[] instruction) { 
        return encodeLoadStore(instruction, 1); 
    }

    public int str(String[] instruction) { 
        return encodeLoadStore(instruction, 0); 
    }


    private int encodeLoadStore(String[] instruction, int loadBit){
        int cond = getConditionCode(instruction[0]);
        int rd;
        int rn;
        int offset = 0;
        int I = 0;          
        int P = 0;          
        int U = 0;          
        int B = 0;          
        int W = 0;          
        int L = loadBit;  

        int rdIndex;
        int addrIndex;

        if (cond != 0b1110){
            rdIndex = 2;
            addrIndex = 3;
        } else{
            rdIndex = 1;
            addrIndex = 2;
        }

        rd = getRegister(instruction[rdIndex]);

        String address = instruction[addrIndex].replace("[","").replace("]","").trim();

        String[] parts = address.split(",");

        rn = getRegister(parts[0]);

        if (parts.length > 1){
            offset = parseImmediate(parts[1]) & 0xFFF;
        }

        int machineCode =
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

        System.out.printf("0x%08X\n", machineCode);

        return machineCode;
    }
}