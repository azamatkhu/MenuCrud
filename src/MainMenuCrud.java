import java.sql.*;
import java.util.Scanner;

public class MainMenuCrud {
    public static void main(String[] args) {
        int opcion = -1;
        Scanner sc = new Scanner(System.in);
        String sql = "";
        // Bucle while para manejar el menu
        while(opcion != 0) {
            System.out.println("----- MENU SQL------");
            System.out.println("1. Insertar empleado");
            System.out.println("2. Mostrar empleado");
            System.out.println("3. Actualizar empleado");
            System.out.println("4. Eliminar empleado");
            System.out.println(" ");
            System.out.println("0. Salir");
            System.out.println("Elige la opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();
            // Conexion a database
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "RIBERA",
                        "ribera"
                );

                System.out.println("Conectado!");

                Statement statement = connection.createStatement();

                PreparedStatement ps;

                // Switch para manejar los opciones
                // Cada case tiene su propia sql consulta
                switch(opcion) {
                    case 1:
                        System.out.println("Introduce el identificador del empleado: ");
                        int idEmpleado = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Introduce el nombre del empleado: ");
                        String nombreEmpleado = sc.nextLine();

                        System.out.println("Introduce el salario del empleado: ");
                        double salarioEmpleado = sc.nextDouble();

                        sql = "INSERT INTO EMPLEADO (id, nombre, salario) VALUES (?, ?, ?)";
                        ps = connection.prepareStatement(sql);

                        ps.setInt(1, idEmpleado);
                        ps.setString(2, nombreEmpleado);
                        ps.setDouble(3, salarioEmpleado);

                        ps.executeUpdate();

                        System.out.println("Se ha insertado el empleado " + idEmpleado + "!");
                        break;
                    case 2:
                        sql = "SELECT * FROM EMPLEADO WHERE ID = ?";
                        Statement state = connection.createStatement();

                        ResultSet resultSet = state.executeQuery(sql);

                        while (resultSet.next()) {
                            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("nombre") + " " + resultSet.getDouble("salario"));
                        }

                        break;
                    case 3:
                        System.out.println("Introduce el identificador del empleado: ");
                        int idActualizar = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Que campo quieres actualizar? ");
                        String campo = sc.nextLine();
                        // Aqui ponemos un switch, para saber que campo exacto quieremos actualizar
                        switch(campo) {
                            case "nombre":
                                System.out.println("Introduce el nombre del empleado: ");
                                String nombreActualizar = sc.nextLine();

                                sql = "UPDATE EMPLEADO SET NOMBRE = ? WHERE ID = ?";
                                ps = connection.prepareStatement(sql);
                                ps.setString(1, nombreActualizar);
                                ps.setInt(2, idActualizar);

                                ps.executeUpdate();
                                break;
                            case "salario":
                                System.out.println("Introduce el salario del empleado: ");
                                double salarioActualizar = sc.nextDouble();

                                sql = "UPDATE EMPLEADO SET SALARIO = ? WHERE ID = ?";
                                ps = connection.prepareStatement(sql);
                                ps.setDouble(1, salarioActualizar);
                                ps.setInt(2, idActualizar);

                                ps.executeUpdate();
                                break;
                            default:
                                System.out.println("No existe este campo!");

                                break;
                        }

                        System.out.println("Se ha modificado el empleado " + idActualizar + "!");

                        break;
                    case 4:
                        System.out.println("Introduce el identificador del empleado: ");
                        int idEliminar = sc.nextInt();
                        sc.nextLine();

                        sql = "DELETE FROM EMPLEADO WHERE ID = ?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, idEliminar);

                        ps.executeUpdate();
                        System.out.println("Se ha eliminado  el empleado " + idEliminar + "!");
                        break;
                    case 0:
                        System.out.println("Saliendo....");
                        break;
                    default:
                        System.out.println("La opcion no es valida!");
                        break;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
