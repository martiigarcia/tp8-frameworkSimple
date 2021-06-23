package garcia.framework;

import org.reflections.Reflections;

import java.util.*;

public class MiAplicacion {

    private List<Accion> acciones = new ArrayList<>();

    public MiAplicacion() {

    }

    public void init() {

        //escanear proyecto y retorna las clases que implementen la interfaz Accion. Devuelve clases no instancias
        Set<Class<? extends Accion>> clases = new Reflections("").getSubTypesOf(Accion.class);
        clases.forEach(clase -> {
            try {
                acciones.add(clase.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("");
            }
        });
        ordenarListaAccionesPorNombreDeClase();
        mostrarMenu();

    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        String opcion = "";
        System.out.println("--------------------------");
        System.out.println("--------BIENVENIDO--------");
        System.out.println("--------------------------");
        do {
            System.out.println("Opciones a elegir: ");
            for (int valor = 0; valor < acciones.size(); valor++) {
                System.out.println((valor + 1) + " --> " + acciones.get(valor).nombreItemMenu() + ": " + acciones.get(valor).descripcionItemMenu());
            }
            System.out.print("Ingrese el numero de la opcion seleccionada e ingrese enter: ");
            acciones.get(scanner.nextInt() - 1).ejecutar();
            System.out.println("\nSi desea seguir ejecutando acciones ingrese \"SI\". Si ingresa otro caracter la ejecucion terminara.");
            opcion = scanner.next();

        } while (opcion.equalsIgnoreCase("si"));
        System.out.println("--------------------------");
        System.out.println("-----FIN DE EJECUCION-----");
        System.out.println("--------------------------");
    }

    private void ordenarListaAccionesPorNombreDeClase() {
        List<String> list = new ArrayList<>();
        List<Accion> listAccionesOrdenadas = new ArrayList<>();
        for (Accion accion : acciones) {
            list.add(accion.getClass().getName());
        }
        Collections.sort(list);
        for (String string : list) {
            for (Accion accion : acciones) {
                if (string.equals(accion.getClass().getName())) {
                    listAccionesOrdenadas.add(accion);
                }
            }
        }
        this.acciones = listAccionesOrdenadas;
    }


}

