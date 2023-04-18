import java.math.BigInteger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
// java program to find multiplicative modulo
// inverse using Extended Euclid algorithm.
class GFG {

    // Global Variables
    public static BigInteger x;
    public static BigInteger y;

    // Function for extended Euclidean Algorithm
    public static BigInteger gcdExtended(BigInteger a, BigInteger b)
    {

        // Base Case
        if (a.equals(new BigInteger("0"))) {
            x = new BigInteger("0");
            y = new BigInteger("1");
            return b;
        }

        // To store results of recursive call
        BigInteger gcd = gcdExtended(b.mod(a), a);
        BigInteger x1 = x;
        BigInteger y1 = y;

        // Update x and y using results of recursive
        // call
        BigInteger tmp = b.divide(a);
        x = y1.subtract( tmp.multiply(x1));
        y = x1;

        return gcd;
    }

    public static BigInteger modInverse(BigInteger A, BigInteger M)
    {
        BigInteger res=null;
        BigInteger g = gcdExtended(A, M);
        if (!g.equals(new BigInteger("1"))) {
            System.out.println("Inverse doesn't exist");
        }
        else {

            // m is added to handle negative x
            res = (x.mod(M).add(M)).mod(M);

        }
        return res;
    }
}

// The code is contributed by Gautam goel (gautamgoel962)

public class Main {
    public static BigInteger L(BigInteger x , BigInteger n)
    {
        return x.subtract(new BigInteger("1")).divide(n);
    }
    public static BigInteger encryption(BigInteger g, BigInteger m, BigInteger n)
    {
        GFG gfg=new GFG();
        BigInteger r = new BigInteger("11");
        if(gfg.gcdExtended(n,r).equals(new BigInteger("1")))
        {
            //g^m * r^n mod n^2 = ((g^m mod n2)*(r^n mod n^2)) mod n^2
            BigInteger k1= g.modPow(m,n.multiply(n));
            BigInteger k2 = r.modPow(n,n.multiply(n));
            BigInteger cipher = k1.multiply(k2).mod(n.multiply(n));
            return cipher;

        }
        else
        {
            System.out.println("Wrong R");
            return null;
        }

    }
    public static BigInteger decryption(BigInteger c, BigInteger lamda, BigInteger n,BigInteger mu)
    {
        BigInteger message = null;
        BigInteger t1= c.modPow(lamda,n.multiply(n));
        t1= L(t1,n);

        message = t1.multiply(mu).mod(n);

        return message;
    }
    public static void main(String[] args) {
        BigInteger p,q;
        GFG gfg=new GFG();
        p=new BigInteger("10007");
        q=new BigInteger("10069");

        BigInteger n=p.multiply(q); //p*q
        BigInteger phi= p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));

        if(gfg.gcdExtended(n,phi).equals(new BigInteger("1")))
        {
            BigInteger lamda = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));
            lamda = lamda.divide(gfg.gcdExtended(p.subtract(new BigInteger("1")),q.subtract(new BigInteger("1"))));
            BigInteger g= new BigInteger("13");
            if(gfg.gcdExtended(n,g).equals(new BigInteger("1")))
            {
                BigInteger t1=g.modPow(lamda,n.multiply(n));
                t1=L(t1,n);
                BigInteger mu = gfg.modInverse(t1,n);
                BigInteger message = new BigInteger("118");
                BigInteger cipher = encryption(g,message,n);

                System.out.println(cipher);

                BigInteger rm = decryption(cipher,lamda,n,mu);

                System.out.println(rm);

            }
            else {
                System.out.println("Wrong G");
            }
        }
        else
        {
            System.out.println("Wrong Prime");
        }




    }
}