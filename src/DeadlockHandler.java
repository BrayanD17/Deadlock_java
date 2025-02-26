import java.util.List;

/**
 * Clase encargada de detectar y resolver deadlocks en la asignación de recursos.
 */
public class DeadlockHandler {
    private GestorRecursos gestor;

    /**
     * Constructor de DeadlockHandler.
     *
     * @param gestor Referencia al gestor de recursos que administra los procesos y los recursos.
     */
    public DeadlockHandler(GestorRecursos gestor) {
        this.gestor = gestor;
    }

    /**
     * Detecta si existe un deadlock en el sistema.
     *
     * @param procesos   Lista de procesos en ejecución.
     * @param disponible Array de recursos disponibles en el sistema.
     * @return true si hay un deadlock, false si no lo hay.
     */
    public boolean detectarDeadlock(List<Proceso> procesos, int[] disponible) {
        for (Proceso proceso : procesos) {
            if (proceso.estaBloqueado(disponible)) { // Si hay un proceso bloqueado sin recursos disponibles
                return true;
            }
        }
        return false;
    }

    /**
     * Resuelve un deadlock liberando los recursos de un proceso bloqueado.
     *
     * @param procesos   Lista de procesos en ejecución.
     * @param disponible Array de recursos disponibles en el sistema.
     */
    public void resolverDeadlock(List<Proceso> procesos, int[] disponible) {
        for (Proceso proceso : procesos) {
            int[] asignado = proceso.getAsignado();
            boolean liberado = false;

            // Liberar todos los recursos asignados al primer proceso encontrado en deadlock
            for (int j = 0; j < asignado.length; j++) {
                if (asignado[j] > 0) {
                    disponible[j] += asignado[j]; // Devuelve los recursos al sistema
                    asignado[j] = 0; // Reinicia la asignación del proceso
                    liberado = true;
                }
            }

            // Si se liberaron recursos, se asume que el proceso es eliminado para desbloquear el sistema
            if (liberado) {
                System.out.println("Proceso " + proceso.getId() + " eliminado para liberar recursos.");
                break;
            }
        }
    }
}
