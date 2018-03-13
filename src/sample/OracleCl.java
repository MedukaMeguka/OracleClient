package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OracleCl {
    private final StringProperty FAM;
    private final StringProperty OT;
    private final StringProperty IM;
    private final StringProperty SPEC;
    private final StringProperty GR;
    private final IntegerProperty STUD_ID;
    private final IntegerProperty NO_ZK;
    private final IntegerProperty KURS;


    public OracleCl(Integer STUD_ID, Integer NO_ZK, String FAM, String IM, String OT,  String SPEC,Integer KURS, String GR)  {
        this.FAM = new SimpleStringProperty(FAM);
        this.OT = new SimpleStringProperty(OT);
        this.IM = new SimpleStringProperty(IM);
        this.SPEC = new SimpleStringProperty(SPEC);
        this.GR = new SimpleStringProperty(GR);
        this.STUD_ID = new SimpleIntegerProperty(STUD_ID);
        this.NO_ZK = new SimpleIntegerProperty(NO_ZK);
        this.KURS = new SimpleIntegerProperty(KURS);
    }

    public String getFam() {return FAM.get(); }

    public StringProperty FAMProperty() {
        return FAM;
    }

    public String getOT() {
        return OT.get();
    }

    public StringProperty OTProperty() {
        return OT;
    }

    public String getIM() {
        return IM.get();
    }

    public StringProperty IMProperty() {
        return IM;
    }

    public String getSPEC() {
        return SPEC.get();
    }

    public StringProperty SPECProperty() {
        return SPEC;
    }

    public String getGR() {
        return GR.get();
    }

    public StringProperty GRProperty() {
        return GR;
    }

    public int getKURS() {
        return KURS.get();
    }

    public IntegerProperty KURSProperty() {
        return KURS;
    }

    public IntegerProperty NO_ZKProperty() {
        return NO_ZK;
    }

    public int getNO_ZK() {
        return NO_ZK.get();
    }

    public int getSTUD_ID() {
        return STUD_ID.get();
    }

    public IntegerProperty STUD_IDProperty() {
        return STUD_ID;
    }

}
