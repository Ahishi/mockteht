package main;

public interface IHinnoittelija {
    public abstract float getAlennusProsentti(Asiakas asiakas, Tuote tuote);

    void aloita();

    void lopeta();
}
