package main;

public class ThreadingStudy extends Thread{
    private static int amount =0;

    @Override
    public void run() {
        amount++;
        System.out.println("Thread started");
    }

    public static void main(String[] args) {
        ThreadingStudy a = new ThreadingStudy();
        a.start();
        while (a.isAlive()){
            System.out.println("waiting");
        }
        System.out.println(amount + "main");
        amount++;
        System.out.println(amount);
    }
}
