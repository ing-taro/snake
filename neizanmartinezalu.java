import java.awt.Point;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import uhu.snake.Agente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class neizanmartinezalu extends Agente {

    private class Nodo implements Comparable<Nodo> {
        Point pos;
        Nodo padre;
        int g, h, f;

        public Nodo(Point pos, Nodo padre) {
            this.pos = pos;
            this.padre = padre;
            this.g = (padre != null) ? padre.g + 1 : 0;
            this.h = Math.abs(pos.x - COMIDA.x) + Math.abs(pos.y - COMIDA.y);
            this.f = g + h;
        }

        @Override
        public int compareTo(Nodo o) {
            return Integer.compare(this.f, o.f);
        }
    }

    public neizanmartinezalu() {
        super();
    }

    @Override
    public List<String> pensar() {
        List<String> acciones = new ArrayList<>();

        Point cabeza = CUERPO.get(0);
        List<Point> camino = buscarCamino(cabeza, COMIDA);

        if (!camino.isEmpty()) {
            for (Point siguiente : camino) {
                acciones.add(direccion(cabeza, siguiente));
                cabeza = siguiente;
            }
        } else {
            // Si no hay camino, intenta un movimiento válido
            for (String dir : ACCIONES) {
                Point intento = mover(cabeza, dir);
                if (esMovimientoSeguro(intento)) {
                    acciones.add(dir);
                    break;
                }
            }
        }

        return acciones;
    }

    private List<Point> buscarCamino(Point inicio, Point meta) {
        PriorityQueue<Nodo> abiertos = new PriorityQueue<>();
        Set<Point> cerrados = new HashSet<>();
        abiertos.add(new Nodo(inicio, null));

        while (!abiertos.isEmpty()) {
            Nodo actual = abiertos.poll();

            if (actual.pos.equals(meta)) {
                return reconstruirCamino(actual);
            }

            if (cerrados.contains(actual.pos)) continue;
            cerrados.add(actual.pos);

            for (Point vecino : vecinosValidos(actual.pos)) {
                if (!cerrados.contains(vecino)) {
                    abiertos.add(new Nodo(vecino, actual));
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Point> vecinosValidos(Point actual) {
        List<Point> vecinos = new ArrayList<>();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] d : dirs) {
            Point p = new Point(actual.x + d[0], actual.y + d[1]);
            if (esMovimientoSeguro(p)) {
                vecinos.add(p);
            }
        }
        return vecinos;
    }

    private List<Point> reconstruirCamino(Nodo nodo) {
        List<Point> camino = new LinkedList<>();
        Nodo actual = nodo;

        while (actual.padre != null) {
            camino.add(0, actual.pos);
            actual = actual.padre;
        }

        return camino;
    }

    private String direccion(Point origen, Point destino) {
        if (destino.x > origen.x) return "DERECHA";
        if (destino.x < origen.x) return "IZQUIERDA";
        if (destino.y > origen.y) return "ABAJO";
        return "ARRIBA";
    }

    private boolean esMovimientoSeguro(Point p) {
        return p.x >= 0 && p.x < ANCHO &&
               p.y >= 0 && p.y < ALTO &&
               !CUERPO.subList(0, CUERPO.size() - 1).contains(p);
    }

    private Point mover(Point p, String dir) {
        if ("DERECHA".equals(dir)) {
            return new Point(p.x + 1, p.y);
        } else if ("IZQUIERDA".equals(dir)) {
            return new Point(p.x - 1, p.y);
        } else if ("ABAJO".equals(dir)) {
            return new Point(p.x, p.y + 1);
        } else if ("ARRIBA".equals(dir)) {
            return new Point(p.x, p.y - 1);
        } else {
            return p;
        }
    }
}