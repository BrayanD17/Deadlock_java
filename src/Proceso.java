import java.util.Random;

/**
 * Clase que representa un proceso en el sistema, el cual puede solicitar y liberar recursos.
 */
public class Proceso {
    private int id;
    private int[] asignado;  // Recursos actualmente asignados al proceso
    private int[] solicitado; // Recursos que el proceso ha solicitado
    private Random random = new Random();

    /**
     * Constructor de la clase Proceso.
     *
     * @param id          Identificador único del proceso.
     * @param numRecursos Número total de tipos de recursos disponibles en el sistema.
     */
    public Proceso(int id, int numRecursos) {
        this.id = id;
        this.asignado = new int[numRecursos];
        this.solicitado = new int[numRecursos];
    }

    /**
     * Solicita un recurso específico. Si está disponible, se asigna al proceso.
     * Si no está disponible, el proceso queda en espera.
     *
     * @param recurso    Índice del recurso solicitado.
     * @param disponible Array con la cantidad de recursos disponibles en el sistema.
     */
    public void solicitarRecurso(int recurso, int[] disponible) {
        if (disponible[recurso] > 0) {
            asignado[recurso]++;
            disponible[recurso]--;
            solicitado[recurso] = 0; // Deja de estar en espera
            System.out.println("Proceso " + id + " obtiene recurso " + recurso);
        } else {
            solicitado[recurso] = 1; // Marca el recurso como solicitado
            System.out.println("Proceso " + id + " espera recurso " + recurso);
        }
    }

    /**
     * Libera un recurso que el proceso tiene asignado y lo devuelve al sistema.
     *
     * @param recurso    Índice del recurso a liberar.
     * @param disponible Array con la cantidad de recursos disponibles en el sistema.
     */
    public void liberarRecurso(int recurso, int[] disponible) {
        if (asignado[recurso] > 0) {
            asignado[recurso]--;
            disponible[recurso]++;
            System.out.println("Proceso " + id + " libera recurso " + recurso);
        }
    }

    /**
     * Selecciona aleatoriamente un recurso dentro del rango de recursos disponibles.
     *
     * @param numRecursos Número total de tipos de recursos en el sistema.
     * @return Índice de un recurso seleccionado aleatoriamente.
     */
    public int elegirRecursoAleatorio(int numRecursos) {
        return random.nextInt(numRecursos);
    }

    /**
     * Selecciona aleatoriamente una acción que el proceso debe realizar.
     *
     * @return 0 si el proceso solicitará un recurso, 1 si liberará un recurso.
     */
    public int elegirAccion() {
        return random.nextInt(2); // 0: Solicitar, 1: Liberar
    }

    /**
     * Determina si un proceso está bloqueado esperando recursos.
     *
     * @param disponible Array con la cantidad de recursos disponibles en el sistema.
     * @return true si el proceso está bloqueado, false si aún puede ejecutarse.
     */
    public boolean estaBloqueado(int[] disponible) {
        for (int i = 0; i < solicitado.length; i++) {
            if (solicitado[i] == 1 && disponible[i] > 0) {
                return false; // Si hay al menos un recurso disponible que necesita, no está bloqueado
            }
        }
        return true; // Está bloqueado si no puede obtener ningún recurso que necesita
    }

    /**
     * Obtiene los recursos asignados actualmente al proceso.
     *
     * @return Array con la cantidad de recursos asignados al proceso.
     */
    public int[] getAsignado() {
        return asignado;
    }

    /**
     * Obtiene el identificador del proceso.
     *
     * @return ID del proceso.
     */
    public int getId() {
        return id;
    }
}
