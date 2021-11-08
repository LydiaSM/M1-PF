package ex2;

import java.util.function.Predicate;

public class App {

    public static void q1() {
        Predicate<Pair<Integer, Double>> TailleTropPetite = pair -> pair.fst < 100;
        Predicate<Pair<Integer, Double>> TailleTropGrande = pair -> pair.fst > 200;
        Predicate<Pair<Integer, Double>> TailleIncorrecte = TailleTropPetite.or(TailleTropGrande);
        Predicate<Pair<Integer, Double>> TailleCorrecte = (TailleTropPetite.and(TailleTropGrande)).negate();
        Predicate<Pair<Integer, Double>> PoidsTropLourd = pair -> pair.snd > 150.0;
        Predicate<Pair<Integer, Double>> PoidsCorrect = PoidsTropLourd.negate();
        Predicate<Pair<Integer, Double>> AccesAutorise = TailleCorrecte.and(PoidsCorrect);
    }

    public static void main(String[] args) {
        App.q1();
    }
}