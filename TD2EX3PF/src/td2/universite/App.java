package td2.universite;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class App {

	//Question 1
    static Predicate<Etudiant> tousLesEtudiants = etudiant -> true;
    
    public static void afficherSi(String enTete, Predicate<Etudiant> condition, Annee annee) {
        System.out.println(enTete);
        annee.etudiants().forEach(etudiant -> {
            if(condition.test(etudiant))
                System.out.println(etudiant);
        });
    }
    
    //Question 2
    static Predicate<Etudiant> aDEF = etudiant -> {
        Set<Matiere> matieres = App.MatieresDeLAnnee(etudiant.annee());
        for(Matiere matiere : matieres) {
            if(!etudiant.notes().containsKey(matiere))
                return true;
        }
        return false;
    };
    
    public static Set<Matiere> MatieresDeLAnnee(Annee annee) {
        Set<Matiere> matieres = new HashSet<>();
        annee.ues().forEach(ue -> ue.ects().forEach((matiere, integer) -> matieres.add(matiere)));
        return matieres;
    }
    
    //Question 3
    static Predicate<Etudiant> aNoteEliminatoire = etudiant -> {
        for(Map.Entry<Matiere, Double> note : etudiant.notes().entrySet()) {
            if(note.getValue() < 6) {
                return true;
            }
        }
        return false;
    };
    
    //Question 4
    public static Double moyenne(Etudiant etudiant) {
        if(aDEF.test(etudiant)) {
        	return null;
        }
        
        double moyenne = 0.0;
        int coef = 0;
        for(UE ue : etudiant.annee().ues()) {
            for(Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                Matiere matiere = ects.getKey();
                Integer credit = ects.getValue();
                Double note = etudiant.notes().get(matiere);
                moyenne = moyenne + note * credit;
                coef = coef + credit;
            }
        }
        moyenne = moyenne / coef;
        return moyenne;
    }

    //Question 5
    static Predicate<Etudiant> naPasLaMoyennev1 = etudiant -> {
        return moyenne(etudiant) < 10;
    };
    //Renvoie une valeur null, il y a une exception

    //Question 6
    static Predicate<Etudiant> naPasLaMoyennev2 = etudiant -> {
        try {
            return moyenne(etudiant) < 10;
        } catch (NullPointerException e) {
            return true;
        }
    };

    //Question 7
    //static Predicate<Etudiant> session2v1 = naPasLaMoyennev1.or(aNoteEliminatoire).or(aDEF);
    //Il y a une exception donc:
    static Predicate<Etudiant> session2v1 = aNoteEliminatoire.or(aDEF).or(naPasLaMoyennev1);

    //Question 8
    static Function<Etudiant, String> afficher = new Function<Etudiant, String>() {
        public String apply(Etudiant etudiant) {
            double moyenne = moyenne(etudiant);
            //double moyenne = moyenneIndicative(etudiant);
        }
    };

    public static void afficheSiv2(String enTete, Predicate<Etudiant> condition, Annee annee, Function<Etudiant, String> afficher) {
        System.out.println(enTete);
        annee.etudiants().forEach(etudiant -> {
            if(condition.test(etudiant))
                System.out.println(afficher.apply(etudiant));
        });
    }

    //Question 9
    public static Double moyenneIndicative(Etudiant etudiant) {
        double moyenne = 0.0;
        int coef = 0;
        for(UE ue : etudiant.annee().ues()) {
            for(Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                Matiere matiere = ects.getKey();
                Integer credit = ects.getValue();
                Double note = etudiant.notes().get(matiere);
                moyenne = moyenne + note * credit;
                coef = coef + credit;
            }
        }
        moyenne = moyenne / coef;
        return moyenne;
    }
    

    public static void main(String[] args) {
    	
    	Matiere m1 = new Matiere("MAT1");
    	Matiere m2 = new Matiere("MAT2");
    	UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
    	Matiere m3 = new Matiere("MAT3");
    	UE ue2 = new UE("UE2", Map.of(m3, 1));
    	Annee a1 = new Annee(Set.of(ue1, ue2));
    	Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
    	e1.noter(m1, 12.0);
    	e1.noter(m2, 14.0);
    	e1.noter(m3, 10.0);
    	//System.out.println(e1);
    	Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
    	e2.noter(m1, 14.0);
    	e2.noter(m3, 14.0);
    	Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
    	e3.noter(m1, 18.0);
    	e3.noter(m2, 5.0);
    	e3.noter(m3, 14.0);
    	
    	//afficherSi("**TOUS LES ETUDIANTS\n", tousLesEtudiants, a1);
    	//afficherSi("**ETUDIANTS DEFAILLANTS\n", aDEF, a1);
    	//afficherSi("**ETUDIANTS AVEC NOTE ELIMINATOIRE\n", aNoteEliminatoire, a1);
    	//System.out.println(moyenne(e1));
        //afficherSi("**ETUDIANTS SOUS LA MOYENNE (v1)\n", naPasLaMoyennev1, a1);
        //afficherSi("**ETUDIANTS SOUS LA MOYENNE (v2)\n", naPasLaMoyennev2, a1);
        afficherSi("**ETUDIANTS EN SESSION 2 (v2)\n", session2v1, a1);
        afficheSiv2("**TOUS LES ETUDIANTS\n", e -> true , a1, afficher);
    }
}