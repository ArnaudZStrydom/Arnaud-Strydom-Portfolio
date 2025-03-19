import java.lang.Math;;

public class Hashmap {
    public KeyValuePair[] array;
    public PrimeNumberGenerator prime = new PrimeNumberGenerator();

    @Override
    public String toString() {
        String res = String.valueOf(prime.currentPrime()) + "\n";
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                res += "\n";
            }
            res += i + "\t";
            if (array[i] == null) {
                res += "-";
            } else {
                res += array[i].toString();
            }
        }
        return res;
    }

    public String toStringOneLine() {
        String res = String.valueOf(prime.currentPrime()) + "[";
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                res += ",";
            }
            if (array[i] == null) {
                res += "-";
            } else {
                res += array[i].toString();
            }
        }
        return res + "]";
    }

    public Hashmap() {
        array = new KeyValuePair[1];
    }

    public Hashmap(String inp) {
        String[] inpparts = inp.split("\\[|\\]|,");
        int primeNumber = Integer.parseInt(inpparts[0].trim());
        prime = new PrimeNumberGenerator();
        while (this.prime.currentPrime() < primeNumber) {
            this.prime.nextPrime();
        }
        array = new KeyValuePair[inpparts.length - 1];
        for (int i = 1; i < inpparts.length; i++) {
            if (!inpparts[i].trim().equals("-")) {
                String[] keyValue = inpparts[i].trim().substring(1).split(":");
                int studentNumber = Integer.parseInt(keyValue[0]);
                int mark = Integer.parseInt(keyValue[1].substring(0, keyValue[1].length() - 1));
                this.array[i - 1] = new KeyValuePair(studentNumber, mark);
            }
        }
    }

    public int hash(int studentNumber) {
        int hashVal = 0;
        String Sstudnum = String.valueOf(studentNumber);
        char[] ch = new char[Sstudnum.length()];
        for (int i = 0; i < Sstudnum.length(); i++) {
            ch[i] = Sstudnum.charAt(i);
        }
        for (int i = 0; i < Sstudnum.length(); i++){
            hashVal = prime.currentPrime() * hashVal + Character.getNumericValue(ch[i]);
        }
            
        hashVal = Math.abs(hashVal);
        hashVal %= array.length;
        return hashVal;

    }

    public KeyValuePair search(int studentNumber) {
        int hash = hash(studentNumber);
        int count = 0;
    
        while (array[hash] != null && count < 3) {
            if (array[hash].studentNumber == studentNumber) {
                return array[hash];
            }
            count = Math.abs(count) + 1;
            hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            if(count < 3 && array[hash] != null){
                count = count*-1;
                hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            }
            
        }
    
        return null;
    }
    
    public void insert(int studentNumber, int mark) {
        int hash = hash(studentNumber);
        int count = 0;
        KeyValuePair serachvalue = search(studentNumber);
        if(serachvalue != null){
            serachvalue.mark = mark ;
            return;
        }

        if(array[hash] == null) {
            array[hash] = new KeyValuePair(studentNumber, mark);
            return;
        }
    
        while (array[hash] != null  && count < 3) {
            count = Math.abs(count) + 1;
            hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            if(count < 3 && array[hash] != null ){
                count = count*-1;
                hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            }
        }
    
        if (count >= 3) {
            resizeArray();
            insert(studentNumber, mark); 
        }else{
            array[hash] = new KeyValuePair(studentNumber, mark);
        }
    }
    
    public void remove(int studentNumber) {
        int hash = hash(studentNumber);
        int count = 0;
    
        while (array[hash] != null && count < 3) {
            if (array[hash].studentNumber == studentNumber) {
                array[hash] = null;
                return;
            }
            count = Math.abs(count) + 1;
            hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            if(count < 3 && array[hash] != null){
                count = count*-1;
                hash = Math.abs((hash + (count * count) * prime.currentPrime())) % array.length;
            }
        }
    }
    
    private void resizeArray() {
        KeyValuePair[] temp = array;
        int newSize = array.length * 2;
        prime.nextPrime(); 
    
        array = new KeyValuePair[newSize];
    
        for (KeyValuePair kv : temp) {
            if (kv != null) {
                insert(kv.studentNumber, kv.mark); 
            }
        }
    }
}
