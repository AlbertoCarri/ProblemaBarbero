public class App {

    public static void main(String[] args) {
        Barbero barbero = new Barbero(5);

        Thread barberoThread = new Thread(() -> {
            try {
                while (true) {
                    barbero.atenderCliente();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread[] clientes = new Thread[10];
        for (int i = 0; i < 10; i++) {
            clientes[i] = new Thread(new Cliente(barbero));
        }

        barberoThread.start();
        for (Thread clienteThread : clientes) {
            clienteThread.start();
        }
    }
}
