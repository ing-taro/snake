package uhu.snake;

import java.awt.Point;
import java.util.*;

public class Plantilla extends Agente {

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

    public Plantilla() {
        super();
    }

    @Override
    public List<String> pensar() {
        Point cabeza = CUERPO.get(0);
        List<Point> camino = buscarCamino(cabeza, COMIDA);

        if (!camino.isEmpty()) {
            Point siguiente = camino.get(0);
            return List.of(direccion(cabeza, siguiente));
        } else {
            return List.of(mejorMovimientoSeguro());
        }
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
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        for (int[] dir : dirs) {
            Point p = new Point(actual.x + dir[0], actual.y + dir[1]);
            if (esValido(p)) vecinos.add(p);
        }

        return vecinos;
    }

    private boolean esValido(Point p) {
        // Puedes pisar la cola si no hay comida (porque se moverá)
        List<Point> cuerpoSinCabeza = CUERPO.subList(0, CUERPO.size() - 1);
        return p.x >= 0 && p.x < ANCHO &&
               p.y >= 0 && p.y < ALTO &&
               !cuerpoSinCabeza.contains(p);
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

    private String mejorMovimientoSeguro() {
        Point cabeza = CUERPO.get(0);
        Map<String, Point> opciones = Map.of(
            "DERECHA", new Point(cabeza.x + 1, cabeza.y),
            "IZQUIERDA", new Point(cabeza.x - 1, cabeza.y),
            "ABAJO", new Point(cabeza.x, cabeza.y + 1),
            "ARRIBA", new Point(cabeza.x, cabeza.y - 1)
        );

        for (Map.Entry<String, Point> e : opciones.entrySet()) {
            if (esValido(e.getValue())) return e.getKey();
        }

        return "DERECHA"; // fallback si está atrapada
    }
}
