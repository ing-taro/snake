import java.awt.Point;
import java.util.*;
import uhu.snake.Agente;

public class neizanmartinezalu extends Agente {

    private static final String[] DIRECCIONES = {"ARRIBA", "ABAJO", "DERECHA", "IZQUIERDA"};
    private List<Point> cuerpoAuxiliar;
    private Random random;

    // Constructor
    public neizanmartinezalu() {
        super();
        inicializar();
    }

   //iniciar variables
    private void inicializar() {
        random = new Random(); 
        cuerpoAuxiliar = new ArrayList<>();
    }

    // Comportamiento agente
    @Override
    public List<String> pensar() {
        List<String> acciones = new ArrayList<>();
        Point cabeza = CUERPO.get(0);

        Point comida = COMIDA;

        if (cabeza.x < comida.x && esSeguro(new Point(cabeza.x + 1, cabeza.y))) {
            acciones.add("DERECHA");
        } else if (cabeza.x > comida.x && esSeguro(new Point(cabeza.x - 1, cabeza.y))) {
            acciones.add("IZQUIERDA");
        } else if (cabeza.y < comida.y && esSeguro(new Point(cabeza.x, cabeza.y + 1))) {
            acciones.add("ABAJO");
        } else if (cabeza.y > comida.y && esSeguro(new Point(cabeza.x, cabeza.y - 1))) {
            acciones.add("ARRIBA");
        } else {
            // Si no hay movimiento seguro, escoge aleatorio seguro
            for (String dir : DIRECCIONES) {
                Point intento = mover(cabeza, dir);
                if (esSeguro(intento)) {
                    acciones.add(dir);
                    break;
                }
            }
        }

        return acciones;
    }

    private boolean esSeguro(Point p) {
        return p.x >= 0 && p.x < ANCHO &&
               p.y >= 0 && p.y < ALTO &&
               !CUERPO.subList(0, CUERPO.size() - 1).contains(p);
    }

    private Point mover(Point p, String dir) {
        switch (dir) {
            case "DERECHA":
                return new Point(p.x + 1, p.y);
            case "IZQUIERDA":
                return new Point(p.x - 1, p.y);
            case "ABAJO":
                return new Point(p.x, p.y + 1);
            case "ARRIBA":
                return new Point(p.x, p.y - 1);
            default:
                return p;
        }
    }
}
