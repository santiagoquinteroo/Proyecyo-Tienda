package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tienda {

    private final String nombre;
    private final String nit;
    private String telefono;

    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final List<Factura> listaFacturas = new LinkedList<>();
    private Map<String, Producto> listaProductos = new HashMap<>();


    public Tienda(String nombre, String nit, String telefono) {
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }


    public String getNit() {

        return nit;
    }

    public String getNombre() {

        return nombre;
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {

        this.telefono = telefono;
    }

    public String registrarCliente(Cliente cliente) {

        Optional<Cliente> encontrado = buscarCliente(cliente.getDocumento());
        if (encontrado.isEmpty()) {
            listaClientes.add(cliente);
            return "El cliente fue registrado existosamente";
        } else return "Ya existe un cliente con esa información";


    }

    public String eliminarCliente(Cliente cliente) {
        Optional<Cliente> encontrado = buscarCliente(cliente.getDocumento());
        if (encontrado.isPresent()) {
            listaClientes.remove(cliente);
            return "Cliente eliminado con exito";
        } else return "El cliente no existe";

    }

    public Optional<Cliente> buscarCliente(String documento) {
        for (Cliente aux : listaClientes) {
            if (aux.getDocumento().equalsIgnoreCase(documento)) {
                return Optional.of(aux);
            }
        }
        return Optional.empty();

    }

    public String registrarProducto(Producto producto) {

        Optional<Producto> productoEncontrado = buscarProducto(producto.getCodigo());
        if (productoEncontrado.isEmpty()) {

            listaProductos.put(producto.getCodigo(), producto);
            return "Producto registrado existosamente";
        } else return "Ya existe un producto con esos datos";


    }

    public String eliminarProducto(Producto producto) {
        Optional<Producto> productoEncontrado = buscarProducto(producto.getCodigo());
        if (productoEncontrado.isPresent()) {
            listaProductos.remove(producto.getCodigo(), producto);
            return "Producto eliminado con exito";
        } else return "No existe producto con esa información";

    }

    public Optional<Producto> buscarProducto(String codigo) {
        if (listaProductos.containsKey(codigo)) {
            return Optional.of(listaProductos.get(codigo));
        }
        return Optional.empty();
    }

    public String registrarFactura(Factura factura) {
        Optional<Factura> facturaEncontrada = buscarFactura(factura.codigo());
        if (facturaEncontrada.isEmpty()) {
            listaFacturas.add(factura);
            return "Factura registrada con exito";
        } else return "Factura ya existente";

    }

    public String eliminarFactura(Factura factura) {
        Optional<Factura> facturaEncontrada = buscarFactura(factura.codigo());
        if (facturaEncontrada.isPresent()) {
            listaFacturas.remove(factura);
            return "Factura eliminada con exito";
        } else return "Factura no existente";

    }


    public Optional<Factura> buscarFactura(String codigo) {
        for (Factura aux : listaFacturas) {
            if (aux.codigo().equalsIgnoreCase(codigo)) {
                return Optional.of(aux);
            }
        }
        return Optional.empty();
    }

    //Imperativa y declarativa

    public Optional<Cliente> obtenerCliente(String documento) {
        return listaClientes.stream().filter(c -> c.getDocumento().equals(documento)).findFirst();

    }

    public Optional<Factura> obtenerFactura(String codigo) {
        return listaFacturas.stream().filter(factura -> factura.codigo().equals(codigo)).findFirst();

    }

    //1. Obtener los productos con una cantidad disponible mayor igual a 10

    public List<Producto> obtenerMayorezDiez() {
        List<Producto> productosFiltrado = new ArrayList<>();
        for (Producto p : listaProductos.values()) {
            if (p.getCantidadDisponible() >= 10) {
                productosFiltrado.add(p);
            }
        }
        return productosFiltrado;
    }

    //2. Obtener los codigos de los productos con una cantidad disponible mayor igual a 10 y menor q 50
    public List<String> obtenerCodigos() {
        List<String> productosFiltrados = new ArrayList<>();
        for (Producto p : listaProductos.values()) {
            if (p.getCantidadDisponible() >= 10 && p.getCantidadDisponible() < 50) ;

            productosFiltrados.add(p.getCodigo());
        }
        return productosFiltrados;
    }

    //3. Obtener la lista de clientes que hayan comprado el 07/Octubre/2026
    public List<Cliente> obtenerClientesFecha() {
        LocalDate fechaConsulta = LocalDate.of(2026, 10, 7);
        List<Cliente> clientes = new ArrayList<>();
        for (Factura f : listaFacturas) {
            if (f.fecha().isEqual(fechaConsulta)) {
                clientes.add(f.cliente());


            }
        }
        return clientes;
    }

    public List<Cliente> obtenerClientesFecha2() {
        LocalDate fechaConsulta = LocalDate.of(2026, 10, 7);
        List<Cliente> clientes = new ArrayList<>();
        for (Cliente c : listaClientes) {
            if (c.comproEnFecha(fechaConsulta) == true) {
                clientes.add(c);
            }
        }
        return clientes;
    }

    //4. Obtener las facturas que tengan un cliente donde su nombre empiece con R
    public List<Factura> obtenerClientesR() {
        List<Factura> facturasR = new ArrayList<>();
        for (Factura f : listaFacturas) {
            String nombre = f.cliente().getNombreCompleto();
            if (nombre.toUpperCase().startsWith("R")) {
                facturasR.add(f);
            }
        }
        return facturasR;
    }

    // 5. Obtener las facturas donde se haya comprado un celular de marca iPhone 16 pro max
    public List<Factura> obtenerFacturasIphone() {
        List<Factura> facturasIphone = new ArrayList<>();
        for (Factura f : listaFacturas) {
            for (DetalleFactura d : f.listaDetallesFactura()) {
                Producto producto = d.getProducto();
                if (producto.getCategoria() == Categoria.CELULARES && producto.getNombre().equalsIgnoreCase("iPhone 16 Pro Max")) {
                    facturasIphone.add(f);
                }
            }
        }
        return facturasIphone;
    }

    // 6. Obtener las facturas con cliente que el nombre sea Juan y haya comprado un celular de marca iPhone 16 pro max
    public List<Factura> obtenerFacturasIphoneJuan() {
        List<Factura> facturasPedidas = new ArrayList<>();
        boolean juan = false;
        boolean iPhone = false;
        for (Factura f : listaFacturas) {
            String nombre = f.cliente().getNombreCompleto();
            if (nombre.equalsIgnoreCase("Juan")) {
                juan = true;
            }
            for (DetalleFactura d : f.listaDetallesFactura()) {
                Producto producto = d.getProducto();
                if (producto.getCategoria() == Categoria.CELULARES && producto.getNombre().equalsIgnoreCase("iPhone 16 Pro Max")) {
                    iPhone = true;
                }
            }
            if (juan && iPhone) {
                facturasPedidas.add(f);
            }
        }
        return facturasPedidas;

    }
    // 7. Implementar un método reciba una categoría y retorne todos los productos que pertenezcan a ella

    public List<Producto> obtenerProductosCategoria(Categoria categoria) {
        List<Producto> productosCategoria = new ArrayList<>();
        for (Producto p : listaProductos.values()) {
            if (p.getCategoria() == categoria) {
                productosCategoria.add(p);
            }
        }
        return productosCategoria;
    }
    // 8. Implementar un método que reciba un precio mínimo y un precio máximo, y retorne los productos que cuyo precio
    //  se encuentre dentro de ese rango, incluyendo ambos límites
    public List<Producto> obtenerProductosRango (double precioMinimo, double precioMaximo){
        List<Producto> productos = new ArrayList<>();
        for (Producto p : listaProductos.values()){
            if (p.getValor()>=precioMinimo&&p.getValor()<=precioMaximo){
                productos.add(p);
            }
        }
        return productos;
    }
}
