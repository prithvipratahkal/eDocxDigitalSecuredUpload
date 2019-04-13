package com.example.sunshine.edocx.app;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


public class Actual {
    Field Zr;
    Element G1;
    Element g,gpowb;
    Field G0;
    Pairing pairing;
    Actual(){
        this.pairing = PairingFactory.getPairing("params/a.properties");
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        int degree = pairing.getDegree();

        /* Return Zr */
        Zr = pairing.getZr();
    }
}
