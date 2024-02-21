## Problema barbero - Alberto Carricondo

# 1. Decisión del cliente
· Un cliente decide si esperará si el tamaño de la cola de espera es menor que el número
de sillas, es decir, esperará si hay sitio disponible en la barbería. También he puesto que 
si el tamaño de la cola es igual a 1, es decir, hay un cliente, le haré un signal al 
barbero para que se despierte y empiece a atender a los clientes.

# 2. Manejo de la cola de espera
· Para garantizar que los clientes sean atendidos en el orden correcto hacemos un lock.lock, para
asegurarnos de que no haya interferencia con otros hilos que puedan intentar acceder a la vez. Cuando
llega un cliente, como he mencionado antes, se despierta el barbero y se extrae al cliente del principio
de la cola con colaEspera.poll(), esto es lo que nos asegura que el barbero atienda al cliente que más 
tiempo haya estado esperando en la cola de espera.
Después de atender al cliente, hacemos lock.unlock y así permitimos que otros clientes sean atendidos. 
Luego hacemos clienteEsperando.signal() para indicar que ha terminado de atender al cliente.

# 3. Concurrencia y sincronización
· Para asegurar que el barbero no sea despertado por un cliente cuando ya está atendiendo a otro lo 
he hecho mediante la condición de barberoDormido.await(). La clave en sí es la condición del bucle
while, ya que mientras la cola de espera esté vacía, el barbero entra en modo de espera (barberoDormido.await())
y se bloquea en esta condición.
