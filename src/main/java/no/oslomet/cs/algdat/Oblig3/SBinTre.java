package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }



    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q);                   // oppretter en ny node

        if (q == null) {rot = p; }                 // p blir rotnode
        else if (cmp < 0){ q.venstre = p;}       // venstre barn til q
        else {q.høyre = p;}                       // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }


    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        int forekomster = 0;

        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.addLast(rot);

        while (!queue.isEmpty()) {
            Node<T> current = queue.removeFirst();
            int cmp = comp.compare(verdi, current.verdi);
            if (cmp == 0) forekomster++;

            if (current.venstre != null) {
                queue.addLast(current.venstre);
            }
            if (current.høyre != null) {
                queue.addLast(current.høyre);
            }

        }
        return forekomster;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

        while (true) {
            if (p.venstre != null) {p = p.venstre;}
            else if (p.høyre != null) {p = p.høyre;}
            else {return p;}
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        //hvis det er rotnoden
        if(p.forelder==null){
            return null;
        }
        //hvis det er høyrebarn
        else if(p==p.forelder.høyre){
            return p.forelder;
        }
        //hvis det er venstre barn
        else if(p==p.forelder.venstre){
                //Hvis det er enebarn
            if(p.forelder.høyre==null){ return p.forelder;}

            //hvis det ikke er enebarn.
            else {
                return førstePostorden(p.forelder.høyre);
                //Finner den første noden i det nye subtreet med høyrebarnet om rot.
            }
        }
        return null;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node <T> p = førstePostorden(rot);
        while (p!=null){
            oppgave.utførOppgave(p.verdi);
            p=nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    ArrayDeque <Node> queue =  new ArrayDeque<>();
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p==null) return;
        queue.addLast(p);
        while(!queue.isEmpty()){
            Node <T> current = queue.removeFirst();

            if(current.venstre!=null){
                postordenRecursive(current.venstre,oppgave);

            }
            if(current.venstre!=null) {
                postordenRecursive(current.høyre,oppgave);

            }
            oppgave.utførOppgave(current.verdi);
        }

    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
