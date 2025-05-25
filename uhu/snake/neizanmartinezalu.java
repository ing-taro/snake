package uhu.snake;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class neizanmartinezalu extends Agente {

    public neizanmartinezalu() {
        super();
    }

    @Override
    public List<String> pensar() {
        List<String> acciones = new LinkedList<>();
        Point cabeza = CUERPO.get(0); // Posición actual de la cabeza

        // Estrategia voraz mejorada con prevención de colisiones
        if (cabeza.x != COMIDA.x) { // Priorizar movimiento horizontal
            if (cabeza.x < COMIDA.x && esMovimientoSeguro(new Point(cabeza.x + 1, cabeza.y))) {
                acciones.add("DERECHA");
            } else if (esMovimientoSeguro(new Point(cabeza.x - 1, cabeza.y))) {
                acciones.add("IZQUIERDA");
            }
        } else { // Priorizar movimiento vertical
            if (cabeza.y < COMIDA.y && esMovimientoSeguro(new Point(cabeza.x, cabeza.y + 1))) {
                acciones.add("ABAJO");
            } else if (esMovimientoSeguro(new Point(cabeza.x, cabeza.y - 1))) {
                acciones.add("ARRIBA");
            }
        }

        // Fallback: Movimiento alternativo si el camino directo está bloqueado
        if (acciones.isEmpty()) {
            if (esMovimientoSeguro(new Point(cabeza.x + 1, cabeza.y))) {
                acciones.add("DERECHA");
            } else if (esMovimientoSeguro(new Point(cabeza.x - 1, cabeza.y))) {
                acciones.add("IZQUIERDA");
            } else if (esMovimientoSeguro(new Point(cabeza.x, cabeza.y - 1))) {
                acciones.add("ARRIBA");
            } else if (esMovimientoSeguro(new Point(cabeza.x, cabeza.y + 1))) {
                acciones.add("ABAJO");
            }
        }

        return acciones;
    }

    /**
     * Verifica si un movimiento es válido
     * @param nuevaPos Posición a comprobar
     * @return true si no hay colisión con paredes o cuerpo
     */
    private boolean esMovimientoSeguro(Point nuevaPos) {
        // Comprobar límites del tablero
        if (nuevaPos.x < 0 || nuevaPos.x >= ANCHO || 
            nuevaPos.y < 0 || nuevaPos.y >= ALTO) {
            return false;
        }
        
        // Comprobar colisión con el cuerpo (excepto la cola que se moverá)
        return !CUERPO.subList(0, CUERPO.size() - 1).contains(nuevaPos);
    }
}