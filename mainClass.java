package com.example.sunshine.edocx.app;
import java.util.*;
import java.io.*;

import it.unisa.dia.gas.jpbc.*;
//import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class mainClass {
    static EncryptedKey EncryptionFunction(Actual ac,byte[] byt){
        //get random element s
        Element s = ac.Zr.newRandomElement();

        //calculate ck = gpowb^s , sk * G1^s
        //System.out.println("SK before = "+SK);
        Element ck1 = ac.gpowb.duplicate();
        ck1.powZn(s);

        Element ck2 = ac.G1.duplicate();
        ck2.powZn(s);
        //ck2.mulZn(SK);
        EncryptedKey CK = new EncryptedKey();
        CK.ck1 = ck1;
        CK.ck2 = ck2;
        //CK.ract = ract;
        CK.s = s;
        CK.byarr = byt;
        return CK;
        //returm ck1 and ck2
    }


    static void DecryptionFunction(Actual ac,EncryptedKey CK,Element skey,Element SK){

        //Pairing pairing = PairingFactory.getPairing("params/a.properties");
        //randomly choose t
        Element t = ac.Zr.newRandomElement();
        //calculate skey^t
        Element skey1 = skey.duplicate();
        skey1.powZn(t);
        //find N = e(ck1,skey')^(1/t)
        //Element N = ac.pairing.pairing(CK.ck1, skey1);
        Element N = ac.pairing.pairing(CK.ck1, skey1);
        Element temp = ac.Zr.newRandomElement();
        temp.set(1);
        temp.div(t);
        //System.out.println("1/t : "+temp);
        N.powZn(temp);

        //N.invert();
        //k = ck2/(N/e(g,g)^(rs))
        Element edash = ac.G1.duplicate();
        //edash.powZn(CK.ract);
        edash.powZn(CK.s);
        N.div(edash);
        //System.out.println("N after = "+N);
        Element k = CK.ck2.duplicate();
        //System.out.println("k before = "+k);
        k.div(N);

        //System.out.println("SK after = "+SK);
    }

   /* public static void main(String args[]) {
        //System.out.println("Hello");
        Actual ac = new Actual();
        TrustedAuthority ta = new TrustedAuthority();
        ta.setup(ac);
        Element skey;
        Element ract = ac.Zr.newRandomElement();
        skey = ta.keyGen(ac,ract);
        Element SK = ac.Zr.newRandomElement();
        EncryptedKey CK;
        CK = EncryptionFunction(ac,ract,SK);
        DecryptionFunction(ac,CK,skey,SK);
    }*/
}
