package co.edu.uniquindio.poo.model;

public class DetalleFactura {
    private final int cantidadComprada;
    private final double subtotal;
    private final Producto producto;
    private final Factura ownedByFactura;

    public DetalleFactura(int cantidadComprada, double subtotal, Producto producto, Factura ownedByFactura) {
        this.cantidadComprada = cantidadComprada;
        this.subtotal = subtotal;
        this.producto = producto;
        this.ownedByFactura = ownedByFactura;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public Factura getOwnedByFactura() {
        return ownedByFactura;
    }
}
