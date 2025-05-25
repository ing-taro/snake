module uhu.snake {
    requires java.desktop; // Para clases AWT (Point, etc.)
    requires java.base;    // Módulo base (implícito pero recomendado declararlo)

    // Exporta el paquete principal para que otros módulos/agentes puedan acceder
    exports uhu.snake;

    // Abre el paquete para reflexión (necesario para cargar dinámicamente el Agente)
    opens uhu.snake;
}