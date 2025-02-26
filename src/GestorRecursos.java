import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la asignación de recursos a los procesos y detecta posibles deadlocks.
 */
public class GestorRecursos {
    private int numProcesos;
    private int numRecursos;
    private List<Proceso> procesos;
    private int[] disponible;
    private DeadlockHandler deadlockHandler;

    /**
     * Constructor de la clase GestorRecursos.
     *
     * @param numProcesos  Número total de procesos en el sistema.
     * @param numRecursos  Número total de tipos de recursos disponibles.
     */
    public GestorRecursos(int numProcesos, int numRecursos) {
        this.numProcesos = numProcesos;
        this.numRecursos = numRecursos;
        this.procesos = new ArrayList<>();
        this.disponible = new int[]{3, 3, 2}; // Inicialización de recursos disponibles
        this.deadlockHandler = new DeadlockHandler(this);

        // Crear e inicializar procesos
        for (int i = 0; i < numProcesos; i++) {
            procesos.add(new Proceso(i, numRecursos));
        }
    }

    /**
     * Simula la ejecución del sistema durante un número determinado de ciclos.
     * En cada ciclo, los procesos solicitan o liberan recursos de forma aleatoria.
     * También se detecta y resuelve deadlocks si ocurren.
     *
     * @param ciclos Número de ciclos a ejecutar en la simulación.
     */
    public void simular(int ciclos) {
        for (int ciclo = 0; ciclo < ciclos; ciclo++) {
            System.out.println("\n>>> Ciclo " + (ciclo + 1));

            // Iterar sobre cada proceso para solicitar o liberar recursos
            for (Proceso proceso : procesos) {
                int recurso = proceso.elegirRecursoAleatorio(numRecursos);
                int accion = proceso.elegirAccion();

                if (accion == 0) {
                    proceso.solicitarRecurso(recurso, disponible);
                } else {
                    proceso.liberarRecurso(recurso, disponible);
                }
            }

            // Verificar y manejar deadlocks si ocurren
            if (deadlockHandler.detectarDeadlock(procesos, disponible)) {
                System.out.println("\n⚠️ Deadlock detectado. Resolviendo...");
                deadlockHandler.resolverDeadlock(procesos, disponible);
            }

            // Mostrar el estado actual del sistema
            mostrarEstado();
        }
    }

    /**
     * Muestra el estado actual del sistema, incluyendo los recursos disponibles
     * y la asignación de recursos a cada proceso.
     */
    public void mostrarEstado() {
        System.out.println("\nEstado del sistema:");

        // Mostrar recursos disponibles
        System.out.print("Recursos disponibles: ");
        for (int i = 0; i < numRecursos; i++) {
            System.out.print("R" + i + ": " + disponible[i] + "  ");
        }

        // Mostrar la asignación de recursos a cada proceso
        System.out.println("\nProcesos y recursos asignados:");
        for (Proceso proceso : procesos) {
            System.out.print("Proceso " + proceso.getId() + ": ");
            for (int j = 0; j < numRecursos; j++) {
                System.out.print(proceso.getAsignado()[j] + " ");
            }
            System.out.println();
        }
    }
}
