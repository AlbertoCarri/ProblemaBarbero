class Cliente implements Runnable {
    private static int idCounter = 1;
    private int id;
    private Barbero barbero;

    public Cliente(Barbero barbero) {
        this.id = idCounter++;
        this.barbero = barbero;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            barbero.llegaCliente(this);
            barbero.atenderCliente();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}