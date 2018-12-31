package com.leonelacs.tenki;

public class Chiiki {
    String id;
    String shou;
    String shi;
    String ku;

    public Chiiki() {}

    public Chiiki(String id, String shou, String shi, String ku) {
        this.id = id;
        this.shou = shou;
        this.shi = shi;
        this.ku = ku;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShou() {
        return shou;
    }

    public void setShou(String shou) {
        this.shou = shou;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getKu() {
        return ku;
    }

    public void setKu(String ku) {
        this.ku = ku;
    }
}
