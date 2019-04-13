package com.example.sunshine.edocx.app;

import android.app.Activity;
import android.widget.Toast;

import java.util.*;
import java.io.*;

import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

public class TrustedAuthority {
    Element a,b,ract,ck1,ck2,skey;
    void TrustedAuthority(){

    }
    void setup(Actual ac){


        this.a = ac.Zr.newRandomElement();
        this.b = ac.Zr.newRandomElement();

        /* Return G1 */
        ac.G0 = ac.pairing.getG1();
        ac.g = ac.G0.newRandomElement();
        ac.G1 = ac.pairing.pairing(ac.g, ac.g); //e(g,g)
        ac.gpowb = ac.g.duplicate();
        //Calculate g^b
        ac.gpowb.powZn(b);
        //Calculate e(g,g)^a
        ac.G1.powZn(a);
        //Master key : a,b
        //Public kay : G0, g, g^b, G1
        //System.out.println("Element g = "+ ac.g);
        //System.out.println("pairing e(g,g) = "+ ac.G1);
    }

    Element keyGen(Actual ac) {

        //random element r
        this.ract = ac.Zr.newRandomElement();
        Element r = ract.duplicate();
        //sk = g^((a+r)/b)

        r.add(a);
        r.div(b);
        this.skey = ac.g.duplicate();
        //System.out.println(" g : " + g);
        //System.out.println(" sk before : " + sk);
        skey.powZn(r);
        //System.out.println("SK : "+ sk);
        //return sk
        return skey;
    }



}


