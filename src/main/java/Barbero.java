import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barbero {
    private final int numSillas;
    private LinkedList<Cliente> colaEspera;
    private Lock lock;
    private Condition barberoDormido;
    private Condition clienteEsperando;

    public Barbero(int numSillas) {
        this.numSillas = numSillas;
        this.colaEspera = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.barberoDormido = lock.newCondition();
        this.clienteEsperando = lock.newCondition();
    }

    public void llegaCliente(Cliente cliente) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Cliente " + cliente.getId() + " llega a la barbería.");
            Thread.sleep(1000);

            if (colaEspera.size() < numSillas) {
                colaEspera.add(cliente);
                System.out.println("Cliente " + cliente.getId() + " se sienta en la sala de espera.");
                Thread.sleep(1000);

                if (colaEspera.size() == 1) {
                    barberoDormido.signal();
                }
            } else {
                System.out.println("No hay sillas disponibles. Cliente " + cliente.getId() + " se va.");
            }
        } finally {
            lock.unlock();
        }
    }

    public void atenderCliente() throws InterruptedException {
        lock.lock();
        try {
            while (colaEspera.isEmpty()) {
                System.out.println("Barbero duerme. No hay clientes.");
                barberoDormido.await();
            }

            Cliente cliente = colaEspera.poll();
            System.out.println("Barbero atiende al Cliente " + cliente.getId());
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5001));
            lock.unlock();

            lock.lock();
            System.out.println("Barbero termina de atender al Cliente " + cliente.getId());
            clienteEsperando.signal();
        } finally {
            lock.unlock();
        }
    }
}